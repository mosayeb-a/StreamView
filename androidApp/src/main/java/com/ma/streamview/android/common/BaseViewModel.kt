package com.ma.streamview.android.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma.streamview.StreamException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var isLoading by mutableStateOf(false)
        protected set

    fun runCatchingSafely(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                isLoading = true
                block.invoke()
                isLoading = false
            } catch (e: StreamException) {
                println("mylog: error: $e, message: ${e.message.toString()}")
                isLoading = false
                when (e.type) {
                    StreamException.Type.SIMPLE -> {
                        _uiEvent.send(
                            UiEvent.ShowSnackbar(
                                message = e.userFriendlyMessage
                            )
                        )
                    }

                    StreamException.Type.EMPTY_SCREEN -> {
                        _uiEvent.send(
                            UiEvent.ShowSnackbar(
                                message = e.userFriendlyMessage
                            )
                        )
                    }

                    else -> Unit
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _uiEvent.close()
    }
}