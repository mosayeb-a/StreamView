package com.ma.streamview.android.feature.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ma.streamview.android.common.animatedScaleOnTouch
import com.ma.streamview.android.theme.Black
import com.ma.streamview.android.theme.Red
import kotlin.math.absoluteValue



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(
    modifier: Modifier = Modifier,
    previews: List<String?>,
    viewersCount: List<Int>? = null,
    value1: Float = .7f,
    value2: Float = 1.4f,
    value3: Int = 56,
    paddingValues: Dp,
    height: Dp = 100.dp,
    width: Dp = 54.dp,
    content: @Composable (Int) -> Unit,
    isStream: Boolean,
    onClickItem: (Int) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLargeScreen = configuration.screenWidthDp >= 600

    val pagerState =
        rememberPagerState(pageCount = { previews.size }, initialPage = previews.size / 2)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = if (isLargeScreen) 237.dp else 56.dp),
                key = { previews[it]!! },
                modifier = modifier.weight(1f)
            ) { page ->
                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                val scaleFactor by animateFloatAsState(
                    targetValue = value1 + (value2 - value1) * (value2 - pageOffset.absoluteValue),
                    animationSpec = tween(durationMillis = value3)
                )
                val currentAlpha by animateFloatAsState(
                    targetValue = if (pagerState.currentPage == page) 1f else 0.35f,
                    animationSpec = tween(durationMillis = 250)
                )
                val currentBlackColor by animateColorAsState(
                    targetValue = if (pagerState.currentPage == page) Black.copy(alpha = 0.8f) else Black.copy(
                        alpha = 0.3f
                    ),
                    animationSpec = tween(durationMillis = 250)
                )
                val currentRedColor by animateColorAsState(
                    targetValue = if (pagerState.currentPage == page) Red.copy(alpha = 0.8f) else Red.copy(
                        alpha = 0.3f
                    ),
                    animationSpec = tween(durationMillis = 250)
                )
                Box(modifier = modifier
                    .graphicsLayer {
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    .alpha(scaleFactor.coerceIn(0f, 1f))
                    .padding(0.dp)
                    .clip(RoundedCornerShape(2.dp))
                ) {
                    StreamImage(
                        url = previews[page].toString(),
                        modifier = modifier
                            .height(height)
                            .width((width))
                            .alpha(currentAlpha),
                    ) {
                        onClickItem.invoke(page)
                    }
                    StreamLabel(
                        modifier = Modifier.align(Alignment.TopStart),
                        background = currentRedColor,
                        isStream = isStream
                    )
                    ViewerLabel(
                        modifier = Modifier.align(Alignment.BottomStart),
                        background = currentBlackColor,
                        viewersCount = formatNumberWithK(viewersCount?.get(page) ?: 0)
                    )
                }
            }
        }
    }
    if (previews.isNotEmpty()) {
        content.invoke(pagerState.currentPage)
    }
}
@Composable
fun ViewerLabel(modifier: Modifier = Modifier, background: Color, viewersCount: String) {
    Box(
        modifier = modifier
            .padding(6.dp)
            .clip(MaterialTheme.shapes.small)
            .background(background)
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Text(
            text = "$viewersCount Viewers",
            style = MaterialTheme.typography.labelSmall
                .copy(color = MaterialTheme.colorScheme.onSecondary)
        )
    }
}

@Composable
fun StreamLabel(modifier: Modifier = Modifier, background: Color, isStream: Boolean) {
    if (isStream) {
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(6.dp)
                .clip(MaterialTheme.shapes.small)
                .background(background)
                .padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            Text(
                text = "Live",
                style = MaterialTheme.typography.labelMedium
                    .copy(color = MaterialTheme.colorScheme.onSecondary)
            )
        }
    }
}

fun formatNumberWithK(number: Int): String {
    return if (number >= 1000) {
        val thousands = number / 1000.0
        if (thousands % 1 == 0.0) {
            "${thousands.toInt()}K"
        } else {
            String.format("%.1fK", thousands)
        }
    } else {
        number.toString()
    }
}

@Composable
fun StreamImage(
    url: String,
    modifier: Modifier = Modifier,
    scale :ContentScale= ContentScale.Crop ,
    onClick: () -> Unit = {},
) {
    AsyncImage(
        modifier = modifier
            .clickable { onClick.invoke() },
        model = ImageRequest.Builder(LocalContext.current)
//            .scale(scale)
            .crossfade(true)
            .data(url)
            .build(),
        contentDescription = "Image",
        contentScale = scale
    )
}

@Composable
fun SkyAsyncImage(
    modifier: Modifier = Modifier,
    url: String,
    onPictureOfTheDayClicked: () -> Unit = {},
) {
    val painter = rememberAsyncImagePainter(model = url)
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onPictureOfTheDayClicked() },
    ) {

        val state = painter.state
        Image(
            painter = painter,
            contentDescription = "picture of the day",
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.medium)

                .then(
                    (state as? AsyncImagePainter.State.Success)
                        ?.painter
                        ?.intrinsicSize
                        ?.let { intrinsicSize ->
                            Modifier.aspectRatio(intrinsicSize.width / intrinsicSize.height)
                        } ?: Modifier
                ),
        )
        when (state) {
            AsyncImagePainter.State.Empty -> Unit
            is AsyncImagePainter.State.Loading -> Unit
            is AsyncImagePainter.State.Success -> {
            }

            is AsyncImagePainter.State.Error -> Unit
        }
    }
}

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    profilePictureUrl: String,
    onClick: () -> Unit,
    tags: List<String>? = null,
    username: String,
    title: String,
    categoryName: String,
) {
    Row(
        modifier = modifier
            .animatedScaleOnTouch { onClick.invoke() }
    ) {
        StreamImage(
            url = profilePictureUrl,
            modifier = Modifier
                .size(52.dp)
                .clip(MaterialTheme.shapes.large)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(
                text = username,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
            Text(
                modifier = Modifier
                    .width(244.dp),
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier
                    .width(244.dp),
                text = categoryName,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = .7f)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                modifier = Modifier
                    .width(150.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                if (!tags.isNullOrEmpty() && tags.size >= 4) {
                    tags.take(3)
                        .forEach { tag ->
                            Chip(
                                modifier = Modifier.padding(top = 4.dp),
                                onClick = {},
                                label = tag
                            )
                        }
                }
            }
        }
    }
}

/////        val painter = rememberAsyncImagePainter(
////            data = imageUlr
//////            ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
//////                .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
//////                    transformations(TranslationTransformation(parallaxOffset * density))
//////                }).build()
////        )