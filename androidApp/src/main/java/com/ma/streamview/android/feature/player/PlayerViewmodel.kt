package com.ma.streamview.android.feature.player

import android.media.MediaMetadataRetriever
import androidx.annotation.OptIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlaybackException
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import com.ma.streamview.TwitchHelper
import com.ma.streamview.android.common.BaseViewModel
import com.ma.streamview.android.feature.player.navigation.SLUG_NAME
import com.ma.streamview.android.feature.player.navigation.PLAYER_ID
import com.ma.streamview.android.feature.player.navigation.PLAYER_URL
import com.ma.streamview.android.feature.player.navigation.USER_ID
import com.ma.streamview.data.repo.MediaRepository
import com.ma.streamview.data.repo.source.Channel
import com.ma.streamview.data.repo.source.Watched_video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlayerState(
    val videoUrl: String = "",
    val error: String = "",
    val isLoading: Boolean = false,
    val playWhenReady: Boolean = false,
    val currentPosition: Long = 0L,
    val totalPosition: Long = 0L,
    val isFollowed: Boolean = false,
    val isPlayerReady: Boolean = false,
    val isSubOnly: Boolean = false,
)

@OptIn(UnstableApi::class)
@HiltViewModel
class PlayerViewmodel @OptIn(UnstableApi::class) @Inject constructor(
    private val mediaRepository: MediaRepository,
    var player: ExoPlayer,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    //todo: catch the error of the player and if the e is instasce of SourceNotFound-> do something
    private val videoThumb = savedStateHandle.get<String>(PLAYER_URL)!!
    private val videoId = savedStateHandle.get<String>(PLAYER_ID)!!
    private val slugName = savedStateHandle.get<String>(SLUG_NAME)!!
    private val userId = savedStateHandle.get<String>(USER_ID)!!

    var state by mutableStateOf(PlayerState())
        private set

    private val playerListener = object : Player.Listener {
        override fun onPositionDiscontinuity(
            oldPosition: Player.PositionInfo,
            newPosition: Player.PositionInfo,
            reason: Int
        ) {
            updatePositions()
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when (playbackState) {
                Player.STATE_READY -> {
                    state = state.copy(isLoading = false)
                    println("STATEPlayerViewModel. STATE_READY")
                }

                Player.STATE_IDLE -> {
                    println("STATEPlayerViewModel. STATE_IDLE")
                }

                Player.STATE_BUFFERING -> {
                    state = state.copy(isLoading = true)
                    println("STATEPlayerViewModel. STATE_BUFFERING")
                }

                Player.STATE_ENDED -> {
                    println("STATEPlayerViewModel. STATE_BUFFERING")
                }
            }
        }


        override fun onPlayerError(error: PlaybackException) {
            when (error) {
                is ExoPlaybackException -> {
                    when (error.type) {
                        ExoPlaybackException.TYPE_SOURCE -> {
                            state = state.copy(isSubOnly = true, isLoading = true)
                            val urls = TwitchHelper.getVideoUrlMapFromPreviewHelix(url = videoThumb, type = "vod")
                            val url = urls.values.first()
                            setMediaSource(url)
                            state = state.copy(isLoading = false)
                            state = state.copy(videoUrl = url)
                            state = state.copy(isSubOnly = false)
                            state =
                                state.copy(error = "Source error: ${error.sourceException.message}")
                        }

                        ExoPlaybackException.TYPE_RENDERER -> {
                            state =
                                state.copy(error = "Renderer error: ${error.rendererException.message}")
                        }

                        ExoPlaybackException.TYPE_UNEXPECTED -> {
                            state =
                                state.copy(error = "Unexpected error: ${error.unexpectedException.message}")
                        }

                        ExoPlaybackException.TYPE_REMOTE -> {
                            state = state.copy(error = "Remote error")
                        }

                        else -> {
                            state = state.copy(error = "Unknown error: ${error.message}")
                        }
                    }
                }

                else -> {
                    state = state.copy(error = "General error: ${error.message}")
                }
            }
        }
    }

    init {
        player.addListener(playerListener)
        runCatchingSafely {
            val result = mediaRepository.getPlaybackUrl(videoId, "channelLogin")
            state = state.copy(videoUrl = result)
            if (state.videoUrl.isNotEmpty() || state.error.isNotEmpty()) {
                setMediaSource(state.videoUrl)
            }
        }

        viewModelScope.launch {
            val isUserFollowed = mediaRepository.getFollowedChannels()
                .any { it.id == userId }
            state = state.copy(isFollowed = isUserFollowed)
        }
        addToWatchedList(state.currentPosition)
    }


    private fun addToWatchedList(maxWatchedPositions: Long) {
        runCatchingSafely {
            val watchedVideo = Watched_video(
                id = videoId,
                userId = videoId,
                maxPositionSeen = maxWatchedPositions,
                slug = slugName
            )
            mediaRepository.addToWatchedList(
                video = watchedVideo,
                maxWatchedPosition = maxWatchedPositions
            )
        }
    }

    fun removeFromWatchedList(id: String) {
        runCatchingSafely {
            mediaRepository.removeFromWatchedList(id)
        }
    }

    fun followChannel(channel: Channel) {
        viewModelScope.launch {
            if (!state.isFollowed) {
                mediaRepository.followChannel(channel)
                state = state.copy(isFollowed = true)
            }
        }
    }

    fun unfollowChannel(id: String) {
        viewModelScope.launch {
            if (state.isFollowed) {
                mediaRepository.unfollowChannel(id)
                state = state.copy(isFollowed = false)
            }
        }
    }

    private fun setMediaSource(uri: String) {
        val hlsMediaSource = HlsMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(MediaItem.fromUri(uri))
        player.setMediaSource(hlsMediaSource)
        player.prepare()
        player.playWhenReady = true
    }

    override fun onCleared() {
        super.onCleared()
        player.removeListener(playerListener)
        player.release()
    }

    fun updatePositions() {
        state = state.copy(currentPosition = player.currentPosition)
        state = state.copy(totalPosition = player.duration)
    }

    fun seekTo(position: Long) {
        state = state.copy(currentPosition = player.currentPosition)
        player.seekTo(position)
    }

    fun play() {
        if (player.isPlaying) {
            return
        }
        player.playWhenReady = true
    }

    fun pause() {
        if (!player.isPlaying) {
            return
        }
        player.playWhenReady = false
    }

    fun seekForward() {
        val newPosition = minOf(player.currentPosition + 10_000, player.duration)
        state = state.copy(currentPosition = newPosition)
        player.seekTo(newPosition)
    }

    fun seekBackward() {
        val newPosition = maxOf(player.currentPosition - 10_000, 0)
        state = state.copy(currentPosition = newPosition)
        player.seekTo(newPosition)
    }

    fun isPlaying(): Boolean {
        return player.isPlaying
    }
}