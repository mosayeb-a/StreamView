package com.ma.streamview.android.feature.following

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma.streamview.android.common.BaseViewModel
import com.ma.streamview.android.feature.home.HomeState
import com.ma.streamview.data.model.gql.common.StreamEdge
import com.ma.streamview.data.model.gql.common.StreamNode
import com.ma.streamview.data.model.gql.user.UserMedia
import com.ma.streamview.data.model.gql.user.UserNode
import com.ma.streamview.data.model.helix.Stream
import com.ma.streamview.data.repo.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FollowingState(
    val channels: List<UserNode> = emptyList(),
    val liveChannels: List<UserMedia> = emptyList(),
    val usernames: List<String> = emptyList()
)

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : BaseViewModel() {

    var state by mutableStateOf(FollowingState())
        private set

    init {
        runCatchingSafely {
            val followedChannels = mediaRepository.getFollowedChannels()
            println("FollowingViewModel: followedChannels" + followedChannels.map { it.id })
            state = state.copy(channels = followedChannels)

            val userIds = followedChannels.map { it.id }
            val userMedia = mediaRepository.getUserStreams(userIds).data
                ?.userStreams ?: emptyList()
            state = state.copy(liveChannels = userMedia)
    }
}

fun reload() {
    runCatchingSafely {
        val newChannels = mediaRepository.getFollowedChannels()
        if (state.channels.size == newChannels.size) {
            return@runCatchingSafely
        }
        state = state.copy(channels = newChannels)

        val userIds = newChannels.map { it.id }
        val streams = mediaRepository.getUserStreams(userIds).data
            ?.userStreams ?: emptyList()

        state = state.copy(liveChannels = streams)
    }

}

fun getChannelById(id: String) {
    runCatchingSafely { mediaRepository.getChannelById(id) }
}
}
fun <T> notNullList(list: List<T?>?): List<T> {
    val accumulator: MutableList<T> = mutableListOf()
    if (list != null) {
        for (element in list) {
            if (element != null) {
                accumulator.add(element)
            }
        }
    }
    return accumulator
}

public fun <C : MutableCollection<in T>, T : Any> Iterable<T?>.filterNotNullTo(destination: C): C {
    for (element in this) if (element != null) destination.add(element)
    return destination
}