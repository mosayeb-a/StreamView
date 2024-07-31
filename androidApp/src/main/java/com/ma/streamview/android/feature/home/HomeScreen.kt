package com.ma.streamview.android.feature.home

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.ma.basloq.android.components.material.padding
import com.ma.streamview.android.common.UiEvent
import com.ma.streamview.android.components.screens.EmptyScreen
import com.ma.streamview.android.components.screens.EmptyScreenAction
import com.ma.streamview.android.components.screens.LoadingScreen
import com.ma.streamview.android.feature.search.navigation.navigateToSearch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    snackbarHostState: SnackbarHostState,
    onVideoClick: (id: String, url: String, slugName: String, channelLogo: String, userId: String, userName: String, description: String, tags: List<String>?) -> Unit,
    onCategoryClick: (String) -> Unit,
    onUserClick: (id: String, login: String) -> Unit,
    navController: NavController
) {
    val state = viewModel.state
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                println("checkIfRecommendationReady. HomeScreen resumed")
                viewModel.checkIfRecommendationReady()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    LaunchedEffect(key1 = viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true
                    )
                }

                is UiEvent.NavigateUp -> Unit
                else -> Unit
            }
        }
    }

    val sliderValue1 by remember { mutableStateOf(0.848f) }
    val sliderValue2 by remember { mutableStateOf(1.129f) }
    val width by remember { mutableStateOf(235.56.dp) }
    val height by remember { mutableStateOf(134.34.dp) }
    val value3 by remember { mutableStateOf(0) }
    var paddingValue by remember { mutableStateOf(56.dp) }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val topLiveChannels = state.topLiveChannels
        val topLiveChannelsPreviews = topLiveChannels.map { it.streamNode.preview }
        if (topLiveChannelsPreviews.isNotEmpty()) {
            item {
                ImageSlider(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    previews = topLiveChannelsPreviews,
                    value1 = sliderValue1,
                    value2 = sliderValue2,
                    height = height,
                    width = width,
                    value3 = value3,
                    paddingValues = paddingValue,
                    viewersCount = topLiveChannels.map { it.streamNode.viewersCount },
                    content = {
                        topLiveChannels[it].streamNode.freeformTags?.map { tags -> tags.name }
                            ?.let { it1 ->
                                StreamTag(
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        top = 28.dp,
                                        end = 12.dp
                                    ),
                                    channelName = topLiveChannels[it].streamNode.broadcaster.displayName.toString(),
                                    streamTitle = topLiveChannels[it].streamNode.category?.displayName
                                        ?: "N/A",
                                    tags = it1
                                )
                            }
                    },
                    isStream = true,
                    onClickItem = {

                    }
                )
            }
        }

        val categories = state.topCategories
        val previews = state.topCategories.map { it.categoryNode.boxArtURLPreview }
        if (categories.isNotEmpty()) {
            item {
                TitleLabel(primaryText = "TOP", secondaryText = "CATEGORIES")
            }
            item {
                CategoryHorizontalList(
                    modifier = Modifier.padding(top = 8.dp),
                    previews = previews,
                    onClick = {})
            }
        }

        val recommendedVideos = state.recommendedVideos
        val recommendedVideoPreviews = recommendedVideos.map { it.animatedPreviewURL }
        if (recommendedVideoPreviews.isNotEmpty()) {
            item {
                TitleLabel(primaryText = "VIDEOS")
            }
            item {
                HorizontalLazyList(
                    modifier = Modifier.padding(top = 8.dp),
                    previews = recommendedVideoPreviews,
                    viewersCount = recommendedVideos.map { it.viewCount },
                    isStream = false,
                    profilePic = recommendedVideos.map {
                        Log.i(
                            "profilePic", it.owner?.profileImageURL.toString()
                        )
                        it.owner?.profileImageURL
                    },
                    tags = recommendedVideos.map { it.contentTags },
                    usernames = recommendedVideos.map { it.owner?.displayName },
                    categoryNames = recommendedVideos.map { it.game?.displayName.toString() },
                    titles = recommendedVideos.map { it.title },
                    onUserClick = {
                        onUserClick.invoke(
                            recommendedVideos[it].owner?.id.toString(),
                            recommendedVideos[it].owner?.login.toString()
                        )
                    },
                    onVideoClick = {
                        onVideoClick.invoke(
                            recommendedVideos[it].id,
                            recommendedVideos[it].previewThumbnailURL,
                            recommendedVideos[it].game?.slug ?: "",
                            recommendedVideos[it].owner?.profileImageURL.toString(),
                            recommendedVideos[it].owner?.id.toString(),
                            recommendedVideos[it].owner?.login.toString(),
                            recommendedVideos[it].title,
                            recommendedVideos[it].contentTags ?: emptyList()
                        )
                    }
                )
            }
        }

        val recommendedStreams = state.recommendedStreams.map { it.streamNode }
        val recommendedStreamsPreviews = recommendedStreams.map { it.preview }
        if (recommendedStreamsPreviews.isNotEmpty()) {
            item {
                TitleLabel(primaryText = "STREAMS")
            }
            item {
                HorizontalLazyList(
                    previews = recommendedStreamsPreviews,
                    modifier = Modifier.padding(top = 8.dp),
                    viewersCount = recommendedStreams.map { it.viewersCount },
                    isStream = false,
                    profilePic = recommendedStreams.map { it.broadcaster.profileImageURL },
                    tags = recommendedStreams.map {
                        it.freeformTags?.map { it.name } ?: emptyList()
                    },
                    usernames = recommendedStreams.map { it.broadcaster.displayName },
                    categoryNames = recommendedStreams.map { it.category?.displayName.toString() },
                    titles = recommendedStreams.map { it.broadcaster.broadcastSettings.title },
                    onVideoClick = {
                        onVideoClick.invoke(
                            recommendedStreams[it].id,
                            recommendedStreams[it].preview,
                            recommendedStreams[it].category?.displayName ?: "",
                            recommendedStreams[it].broadcaster.profileImageURL.toString(),
                            recommendedStreams[it].broadcaster.id.toString(),
                            recommendedStreams[it].broadcaster.login.toString(),
                            recommendedStreams[it].broadcaster.broadcastSettings.title,
                            recommendedStreams[it].freeformTags?.map { tag -> tag.name }
                        )
                    },
                    onUserClick = {
                        onUserClick.invoke(
                            recommendedStreams[it].broadcaster.id.toString(),
                            recommendedStreams[it].broadcaster.login.toString()
                        )
                    }
                )
            }
        }

        if (viewModel.isHomeEmpty && !viewModel.isLoading) {
            item {
                EmptyScreen(
                    message = "check your network connection or vpn please.",
                    modifier = Modifier
                        .padding(top = 58.dp)
                        .height(400.dp),
                    navController = navController,
                    action = EmptyScreenAction(hint = "Retry", onClick = { viewModel.load() }
                    )
                )
            }
        }

        if ((state.recommendedVideos.isEmpty() || state.recommendedStreams.isEmpty())
            && !viewModel.isLoading && !viewModel.isHomeEmpty
        ) {
            item(state.recommendedVideos) {
                EmptyScreen(
                    message = "you haven't got any recommendation.",
                    modifier = Modifier
                        .padding(top = MaterialTheme.padding.medium),
                    action = EmptyScreenAction(
                        hint = "Explore",
                        onClick = { navController.navigateToSearch() }),
                    navController = navController
                )
            }
        }

        item { Spacer(modifier = Modifier.height(MaterialTheme.padding.extraLarge)) }
    }
    LoadingScreen(displayProgressBar = viewModel.isLoading)
}

@Composable
fun TitleLabel(
    modifier: Modifier = Modifier,
    primaryText: String,
    secondaryText: String = "WE THINK YOU'LL LIKE"
) {
    Row(
        modifier = modifier
            .padding(top = 28.dp, start = 16.dp)
            .fillMaxWidth(),

        ) {
        Text(
            text = "$primaryText ",
            style = MaterialTheme.typography.titleSmall
                .copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
        )
        Text(
            text = secondaryText,
            style = MaterialTheme.typography.titleSmall.copy(
                color = MaterialTheme.colorScheme.secondary
                    .copy(alpha = .7f), fontWeight = FontWeight.Bold
            )
        )
    }
}