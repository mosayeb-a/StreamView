package com.ma.streamview.android.feature.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ma.streamview.android.components.screens.LoadingScreen
import com.ma.streamview.android.common.UiEvent

@Composable
fun ListScreen(
    viewModel: ListViewModel,
    snackbarHostState: SnackbarHostState,
    onVideoClick: (String, String, String, channelLogo: String, userId: String, userName: String, description: String, tags: List<String>?) -> Unit,
    onUserClicked: (String) -> Unit
) {
    val state = viewModel.state
    LaunchedEffect(key1 = viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }

                is UiEvent.NavigateUp -> Unit
                else -> Unit
            }
        }
    }

}

fun showMinText(text: String): String {
    return if (text.length > 15) "${text.substring(0, 15)}..." else text
}