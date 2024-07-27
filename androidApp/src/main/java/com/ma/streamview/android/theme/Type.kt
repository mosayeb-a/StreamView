package com.ma.streamview.android.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ma.streamview.android.R

private val Montserrat = FontFamily(
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semi_bold, FontWeight.Bold),
)

val defaultTextStyle = TextStyle(
    fontFamily = Montserrat,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    ),
    color = Black
)

val BasloqTypography = Typography(
    displayLarge = defaultTextStyle.copy(
        fontSize = 57.sp, lineHeight = 64.sp, letterSpacing = (-0.25).sp
    ),
    displayMedium = defaultTextStyle.copy(
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Bold
    ),
    displaySmall = defaultTextStyle.copy(
        fontSize = 36.sp, lineHeight = 44.sp, letterSpacing = 0.sp
    ),
    headlineLarge = defaultTextStyle.copy(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        lineBreak = LineBreak.Heading,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = defaultTextStyle.copy(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        lineBreak = LineBreak.Heading,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = defaultTextStyle.copy(
        fontSize = 24.sp, lineHeight = 32.sp, letterSpacing = 0.sp, lineBreak = LineBreak.Heading
    ),
    titleLarge = defaultTextStyle.copy(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    ),
    titleMedium = defaultTextStyle.copy(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
    ),
    titleSmall = defaultTextStyle.copy(
        fontSize = 14.sp,
        letterSpacing = 0.sp ,
        fontWeight = FontWeight.Medium,
    ),
    labelLarge = defaultTextStyle.copy(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    ),
    labelMedium = defaultTextStyle.copy(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
    ),
    labelSmall = defaultTextStyle.copy(
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
    ),
    bodyLarge = defaultTextStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        lineBreak = LineBreak.Paragraph,
        fontWeight = FontWeight.Bold,
    ),
    bodyMedium = defaultTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        lineBreak = LineBreak.Paragraph
    ),
    bodySmall = defaultTextStyle.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        lineBreak = LineBreak.Paragraph
    ),
)
