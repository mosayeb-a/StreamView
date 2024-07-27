package com.ma.streamview.android.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ma.streamview.android.common.findMostRepeatedValue
import com.ma.streamview.android.common.BaseViewModel
import com.ma.streamview.data.model.gql.common.CategoryEdge
import com.ma.streamview.data.model.gql.common.StreamEdge
import com.ma.streamview.data.model.gql.common.UserEdge
import com.ma.streamview.data.model.gql.common.VideoNode
import com.ma.streamview.data.repo.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class HomeState(
    val directUrl: String = "",
    val token: String = "",
    val clickedVideo: String = "",
    val error: String = "",
    val videos: List<VideoNode> = emptyList(),
    val recommendedVideos: List<VideoNode> = emptyList(),
    val mostPopularCategory: String = "",
    val recommendedStreams: List<StreamEdge> = emptyList(),
    val topCategories: List<CategoryEdge> = emptyList(),
    val topLiveChannels: List<StreamEdge> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
) : BaseViewModel() {

    // todo: better way to expose the state
//    private val _uiState = MutableStateFlow(HomeState())
//    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    var state by mutableStateOf(HomeState())
        private set

    var isWatchListEmpty by mutableStateOf(false)
        private set

    val isHomeEmpty: Boolean
        get() {
            return state.topLiveChannels.isEmpty() &&
                    state.recommendedVideos.isEmpty() &&
                    state.recommendedStreams.isEmpty()
        }


    init {
        load()
    }

    fun load() {
        runCatchingSafely {
            // get top streams
            val topStreams = mediaRepository.getTopStream(first = 20, after = null, tags = null)
            println("topStream: ${topStreams.data?.topStreams}")
            state = state.copy(topLiveChannels = topStreams.data?.topStreams?.edges ?: emptyList())

            val categories = mediaRepository.getTopCategories(20)
                .data?.topGames?.categoryEdges ?: emptyList()
            state = state.copy(topCategories = categories)

            // find recommended topic
            val watchedList = mediaRepository.getWatchedList()
            println("HOMEVIEWMODEL watchedList: $watchedList")
            if (watchedList.isEmpty()) {
                isWatchListEmpty = true
                return@runCatchingSafely
            }
            val videosSlugName = watchedList.map { it.slug }
            println("HOMEVIEWMODEL videosSlugName: $videosSlugName")
            val mostPopularCategory = findMostRepeatedValue(videosSlugName)
            println("HOMEVIEWMODEL mostPopularCategory: $mostPopularCategory")
            state = state.copy(mostPopularCategory = mostPopularCategory.toString())

            // get recommended videos
            val videos: List<VideoNode> =
                mediaRepository.searchVideos("", mostPopularCategory.toString())
                    .data?.searchFor?.videos?.items!!
            state = state.copy(recommendedVideos = videos)

            // get recommended streams
            val streams = mediaRepository.searchStreams(query = mostPopularCategory.toString())
            state = state.copy(recommendedStreams = streams.data?.searchStreams?.streamEdges!!)
        }
    }
}