package com.ma.streamview.android.feature.profile

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ma.streamview.android.common.BaseViewModel
import com.ma.streamview.android.feature.player.navigation.USER_ID
import com.ma.streamview.android.feature.profile.navigation.LOGIN
import com.ma.streamview.data.model.gql.common.VideoEdge
import com.ma.streamview.data.model.gql.user.User
import com.ma.streamview.data.model.gql.user.UserMedia
import com.ma.streamview.data.model.gql.user.Videos
import com.ma.streamview.data.model.helix.Stream
import com.ma.streamview.data.model.helix.Video
import com.ma.streamview.data.repo.MediaRepository
import com.ma.streamview.data.repo.source.Channel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ProfileState(
    val videos: List<VideoEdge> = emptyList(),
    val streams: List<Stream> = emptyList(),
    val user: User? = null,
    val isFollowed: Boolean = false
)

@SuppressLint("SuspiciousIndentation")
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    private val userId = savedStateHandle.get<String>(USER_ID)!!
    private val userLogin = savedStateHandle.get<String>(LOGIN)!!

    init {
        runCatchingSafely {
            val user = mediaRepository.getUser(userId)
                .data?.getUser
            state = state.copy(user = user)

            val videos = mediaRepository.getUserVideos(userId, 30)
                .data.userMedia.videos?.edges ?: emptyList()
            state = state.copy(videos = videos)
        }

        viewModelScope.launch {
            val isUserFollowed = mediaRepository.getFollowedChannels()
                .any { it.id == userId }
            state = state.copy(isFollowed = isUserFollowed)
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

}