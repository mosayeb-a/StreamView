package com.ma.streamview.android.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.ma.streamview.StreamException
import com.ma.streamview.android.common.BaseViewModel
import com.ma.streamview.android.common.UiEvent
import com.ma.streamview.data.model.gql.common.CategoryEdge
import com.ma.streamview.data.model.gql.common.StreamEdge
import com.ma.streamview.data.model.gql.common.UserEdge
import com.ma.streamview.data.model.gql.common.VideoNode
import com.ma.streamview.data.repo.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchState(
    val isLoading: Boolean = false,
    val videos: List<VideoNode> = emptyList(),
    val streams: List<StreamEdge> = emptyList(),
    val categories: List<CategoryEdge> = emptyList(),
    val channels: List<UserEdge> = emptyList(),
    val query: String = "",
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : BaseViewModel() {
    var state by mutableStateOf(SearchState())
        private set

    private var searchJob: Job? = null

    fun onQueryChange(newQuery: String) {
        state = state.copy(isLoading = true)
        searchJob?.cancel()
        if (state.query == newQuery) {
            println("searchViewModel: " + state.query + " == " + newQuery)
            state = state.copy(isLoading = false)
            return
        }
        state = state.copy(query = newQuery)
        if (state.query.isBlank()) {
            state = state.copy(
                isLoading = false,
                videos = emptyList(),
                streams = emptyList(),
                categories = emptyList(),
                channels = emptyList(),
            )
            return
        }
        searchJob = viewModelScope.launch {
//            delay(500)
            search(state.query)
        }
    }

    private suspend fun search(query: String) {
        try {
            val videoResult = mediaRepository
                .searchVideos(cursor = "", query = query)
                .data?.searchFor?.videos?.items ?: emptyList()
            state = state.copy(videos = videoResult, isLoading = false)

            val streamResult = mediaRepository
                .searchStreams(query = query)
                .data?.searchStreams?.streamEdges ?: emptyList()
            state = state.copy(streams = streamResult, isLoading = false)

            val categoryResult = mediaRepository
                .searchCategories(query = query)
                .data?.searchCategories?.categoryEdges ?: emptyList()
            state = state.copy(categories = categoryResult, isLoading = false)

            val channelResult = mediaRepository
                .searchChannels(query = query)
                .data?.searchUsers?.edges ?: emptyList()
            state = state.copy(channels = channelResult, isLoading = false)
        } catch (e: StreamException) {
            state = state.copy(isLoading = false)
            when (e.type) {
                StreamException.Type.SIMPLE -> {
                    _uiEvent.send(UiEvent.ShowSnackbar(e.userFriendlyMessage))
                }

                StreamException.Type.EMPTY_SCREEN -> {
                    _uiEvent.send(UiEvent.ShowErrorScreen(e.userFriendlyMessage))
                }

                else -> Unit
            }
        }
    }
}

