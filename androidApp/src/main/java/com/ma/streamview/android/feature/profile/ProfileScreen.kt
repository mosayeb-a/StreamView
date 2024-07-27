package com.ma.streamview.android.feature.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.size.Scale
import com.ma.basloq.android.components.material.padding
import com.ma.streamview.android.R
import com.ma.streamview.android.common.UiEvent
import com.ma.streamview.android.components.DraggableTabRow
import com.ma.streamview.android.components.StreamToolbar
import com.ma.streamview.android.components.screens.EmptyScreen
import com.ma.streamview.android.components.screens.LoadingScreen
import com.ma.streamview.android.feature.home.StreamImage
import com.ma.streamview.android.feature.list.VerticalListItem
import com.ma.streamview.android.feature.player.navigation.navigateToPlayer
import com.ma.streamview.android.theme.White


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter", "DefaultLocale")
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    onVideoClick: (id: String, url: String, slugName: String, channelLogo: String, userId: String, userName: String, description: String, tags: List<String>?) -> Unit,
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
    val scrollState = rememberLazyListState()
    val alphaValue by remember {
        derivedStateOf {
            val maxScroll = 300
            val scrollOffset = scrollState.firstVisibleItemScrollOffset
            val firstVisibleIndex = scrollState.firstVisibleItemIndex

            if (firstVisibleIndex == 0) {
                when {
                    scrollOffset < maxScroll -> {
                        scrollOffset / maxScroll.toFloat()
                    }

                    else -> 1f
                }.coerceIn(0f, 1f)
            } else {
                1f
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (state.user != null) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(White),
            ) {
                item {
                    val imageTranslationY =
                        remember { derivedStateOf { (scrollState.firstVisibleItemScrollOffset / 2f) } }
                    StreamImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(175.dp)
                            .graphicsLayer { translationY = imageTranslationY.value },
                        url = state.user.bannerImageURL!!,
                        scale = ContentScale.FillWidth
                    )
                }
                if (state.videos.isEmpty() && !viewModel.isLoading) {
                    item {
                        EmptyScreen(
                            modifier = Modifier
                                .padding(top = MaterialTheme.padding.extraLarge),
                            message = "this channel haven't got any videos!",
                        )
                    }
                }
                val videos = state.videos
                if (videos.isNotEmpty()) {
                    items(count = videos.size, key = { videos[it].videoNode.id }) {
                        val video = videos[it].videoNode
                        print("videoNodes: $video")
                        VerticalListItem(
                            modifier = Modifier
                                .background(White),
                            imageUrl = video.thumb,
                            title = video.title,
                            shouldShowUsername = false,
                            slugName = video.game?.displayName.toString(),
                            viewCount = video.viewCount,
                            createdAt = video.createdAt,
                            onClick = {
                                onVideoClick.invoke(
                                    video.id,
                                    video.previewThumbnailURL,
                                    video.game?.slug ?: "",
                                    state.user.profileImageURL.toString(),
                                    state.user.id.toString(),
                                    state.user.displayName?.toLowerCase().toString(),
                                    video.title,
                                    video.contentTags ?: emptyList()
                                )
                            }
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
            StreamToolbar(
                alpha = alphaValue,
                navController = navController,
                shouldShowTitle = true,
                title = state.user.displayName,
                followerCount = state.user.followers?.totalCount,
                shouldSearch = true,
                shouldBack = true,
            )
            LoadingScreen(displayProgressBar = viewModel.isLoading)
        }
    }
}
