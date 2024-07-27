package com.ma.streamview.android.common

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    data object NavigateUp : UiEvent()
    data class ShowErrorScreen(val message: String) : UiEvent()
}
