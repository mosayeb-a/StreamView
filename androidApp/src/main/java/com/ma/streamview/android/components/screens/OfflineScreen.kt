package com.ma.streamview.android.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun OfflineScreen(
    showOfflineMessage: Boolean = false,
    retrying: Boolean = false,
    onRetryClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .shadow(14.dp)
            .background(MaterialTheme.colorScheme.onPrimary),
        contentAlignment = Alignment.Center
    ) {
        EmptyScreen(
            message = if (showOfflineMessage) "You're still offline." else "You're offline. please check your network connection",
            action = EmptyScreenAction(
                hint = "Retry",
                onClick = { onRetryClick.invoke() }
            )
        )
        LoadingScreen(displayProgressBar = retrying)
    }
}