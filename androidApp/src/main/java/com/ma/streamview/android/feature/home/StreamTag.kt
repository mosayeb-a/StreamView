package com.ma.streamview.android.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ma.streamview.android.components.util.setRippleEffectOnClick


@Composable
fun StreamTag(
    modifier: Modifier = Modifier,
    channelName: String,
    streamTitle: String,
    tags: List<String>
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$channelName ",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = streamTitle,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        TagLayout(tags = tags)
    }
}
@Composable
fun TagLayout(tags: List<String>, modifier: Modifier = Modifier) {
    // Horizontal padding between tags
    val horizontalPadding = with(LocalDensity.current) { 4.dp.toPx() }

    // Calculate available width for tags
    Layout(
        content = {
            tags.forEach { tag ->
                Chip(
                    modifier = Modifier.padding(top = 4.dp),
                    onClick = {},
                    label = tag
                )
            }
        }
    ) { measurables, constraints ->
        var widthUsed = 0f
        var lineHeight = 0
        var lineCount = 1

        // Measure each tag and arrange them in lines
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)

            // Check if tag fits in current line
            if (widthUsed + placeable.width > constraints.maxWidth && lineCount < 2) {
                // Start a new line if tag exceeds available width and limit to 2 lines
                widthUsed = 0f
                lineHeight += placeable.height
                lineCount++
            }

            widthUsed += placeable.width + horizontalPadding
            placeable
        }

        val totalHeight = (lineHeight + (placeables.firstOrNull()?.height ?: 0))

        layout(constraints.maxWidth, totalHeight) {
            var xPosition = 0f
            var yPosition = 0

            placeables.forEach { placeable ->
                if (xPosition + placeable.width > constraints.maxWidth && yPosition < lineHeight) {
                    // Start new line if tag exceeds available width and limit to 2 lines
                    xPosition = 0f
                    yPosition += placeable.height
                }

                // Place the tag at the calculated position with horizontal padding
                placeable.placeRelative(xPosition.toInt(), yPosition)
                xPosition += placeable.width + horizontalPadding
            }
        }
    }
}

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    label: String
) {
    Surface(
        modifier = modifier
            .setRippleEffectOnClick { onClick.invoke() },
        color = MaterialTheme.colorScheme.outline,
        shape = MaterialTheme.shapes.small,
    ) {
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
            )

        }
    }
}