package com.ma.streamview.android.feature.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ma.streamview.StreamException
import com.ma.streamview.android.common.BaseViewModel
import com.ma.streamview.android.common.UiEvent
import com.ma.streamview.data.model.gql.common.VideoEdge
import com.ma.streamview.data.repo.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListState(
    val videos: List<VideoEdge> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class ListViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val categoryId = savedStateHandle.get<String>("categoryId")

    var state by mutableStateOf(ListState())
        private set

    init {
    }
}

