package com.ma.streamview.android.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


val BasloqLightColorScheme = lightColorScheme(
    primary = Blue,
    onPrimary = White,
    secondary = Black,
    onSecondary = White,
    outlineVariant = DarkGray,
    outline = Gray,
    tertiary = Cyan,
    onTertiary = Black,
    onSurface = Gray80,
    error = Red,
    surface = White,
    background = White,
//    conta
)

@Composable
fun StreamTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BasloqLightColorScheme,
        typography = BasloqTypography,
        shapes = StreamShapes,
        content = content
    )
}
