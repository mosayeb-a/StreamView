package com.ma.streamview.android.components.material

import StreamTextButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ma.streamview.android.common.StreamRipple
import com.ma.streamview.android.components.util.setRippleEffectOnClick
import com.ma.streamview.android.theme.Black


@Composable
fun SecondrySnackbar(hostState: SnackbarHostState) {
    SnackbarHost(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.Transparent)
            .clip(MaterialTheme.shapes.medium),
        hostState = hostState,
    ) { data ->
        Snackbar(
            modifier = Modifier
                .shadow(2.dp),
            containerColor = MaterialTheme.colorScheme.secondary,
            action = {
                if (data.visuals.actionLabel != null || data.visuals.withDismissAction) {
                    StreamTextButton(
                        rippleColor = MaterialTheme.colorScheme.primary,
                        text = if (data.visuals.withDismissAction) "Dismiss" else data.visuals.actionLabel.toString(),
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { if (data.visuals.withDismissAction) data.dismiss() else data.performAction() })
                }
            }
        ) {
            Text(
                text = data.visuals.message,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}