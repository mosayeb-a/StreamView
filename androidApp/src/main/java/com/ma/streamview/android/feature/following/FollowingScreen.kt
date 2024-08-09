package com.ma.streamview.android.feature.following

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.ma.streamview.android.common.animatedScaleOnTouch
import com.ma.basloq.android.components.material.padding
import com.ma.streamview.android.common.UiEvent
import com.ma.streamview.android.components.DraggableTabRow
import com.ma.streamview.android.components.screens.EmptyScreen
import com.ma.streamview.android.components.screens.EmptyScreenAction
import com.ma.streamview.android.components.screens.LoadingScreen
import com.ma.streamview.android.feature.search.navigation.navigateToSearch
import com.ma.streamview.android.feature.home.StreamImage
import com.ma.streamview.android.feature.list.VerticalListItem


@Composable
fun FollowingScreen(
    viewModel: FollowingViewModel,
    snackbarHostState: SnackbarHostState,
    onUserClicked: (id: String, login: String) -> Unit,
    onStreamClick: (id: String, url: String, slugName: String, channelLogo: String, userId: String, userName: String, description: String, tags: List<String>?) -> Unit,
    navController: NavController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                println("FollowingScreen resumed")
                viewModel.reload()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

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

    DraggableTabRow(
        modifier = Modifier
            .fillMaxSize(),
        tabsList = listOf("Live", "Channels")
    ) { page: Int ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item(key = viewModel.isLoading) {
                LoadingScreen(
                    modifier = Modifier
                        .padding(top = MaterialTheme.padding.extraLarge),
                    displayProgressBar = viewModel.isLoading,
                )
            }
            when (page) {
                0 -> {
                    val userMedia = state.liveChannels
                        .filter { it.stream != null }

                    println("liveChannels " + state.liveChannels)
                    if (userMedia.isNotEmpty()) {
                        items(
                            count = userMedia.size,
//                            key = { stream[it].stream?.id!! }
                        ) {
                            VerticalListItem(
                                imageUrl = userMedia[it].stream?.preview.toString(),
                                title = userMedia[it].stream?.broadcaster?.broadcastSettings?.title.toString(),
                                username = userMedia[it].displayName,
                                slugName = userMedia[it].stream?.category?.displayName.toString(),
                                viewCount = userMedia[it].stream?.viewersCount ?: 0,
                                createdAt = userMedia[it].stream?.createdAt.toString(),
                                onClick = {
                                    onStreamClick(
                                        userMedia[it].stream?.id.toString(),
                                        userMedia[it].stream?.previewImageURL.toString(),
                                        userMedia[it].stream?.category?.slug.toString(),
                                        userMedia[it].profileImageURL.toString(),
                                        userMedia[it].id.toString(),
                                        userMedia[it].login.toString(),
                                        userMedia[it].stream?.broadcaster?.broadcastSettings?.title.toString(),
                                        userMedia[it].stream?.freeformTags?.map { it.name }
                                    )
                                }
                            )
                        }
                    }
                    if (userMedia.isEmpty() && !viewModel.isLoading) {
                        if (state.channels.isEmpty()) {
                            item {
                                EmptyScreen(
                                    modifier = Modifier
                                        .padding(top = MaterialTheme.padding.extraLarge),
                                    message = "you haven't followed anyone yet!",
                                )
                            }
                        } else {
                            item {
                                EmptyScreen(
                                    modifier = Modifier
                                        .padding(top = MaterialTheme.padding.extraLarge),
                                    message = "there aren't any live followed channels!",
                                )
                            }
                        }
                    }
                }

                1 -> {
                    if (state.channels.isNotEmpty()) {
                        items(
                            count = state.channels.size,
                            key = { state.channels[it].id }
                        ) {
                            val channel = state.channels[it]
                            UserItem(
                                profilePic = channel.profileImageURL,
                                onClick = { onUserClicked.invoke(channel.id, channel.login) },
                                username = channel.displayName,
                                caption = channel.lastBroadcast.startedAt,
                            )
                        }
                    }
                    if (state.channels.isEmpty() && !viewModel.isLoading) {
                        item {
                            EmptyScreen(
                                modifier = Modifier
                                    .padding(top = MaterialTheme.padding.extraLarge),
                                message = "you haven't followed anyone yet!\n explore channels",
                                action = EmptyScreenAction("explore") {
                                    navController.navigateToSearch()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    profilePic: String,
    onClick: () -> Unit,
    username: String,
    caption: String,
) {
    Row(
        modifier = modifier
            .animatedScaleOnTouch { onClick.invoke() }
            .fillMaxWidth()
            .padding(
                vertical = MaterialTheme.padding.extraSmall,
                horizontal = MaterialTheme.padding.medium
            ),
    ) {
        StreamImage(
            url = profilePic,
            modifier = Modifier
                .size(64.dp)
                .clip(MaterialTheme.shapes.large),
        )
        Spacer(modifier = Modifier.width(MaterialTheme.padding.extraSmall))
        Column {
            Text(
                text = username,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
            Text(
                text = caption,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = .7f)
                )
            )
        }
    }

}