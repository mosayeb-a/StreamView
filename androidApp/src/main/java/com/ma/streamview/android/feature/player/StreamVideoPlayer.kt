package com.ma.streamview.android.feature.player

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.ma.basloq.android.components.material.padding
import com.ma.streamview.android.R
import com.ma.streamview.android.common.StreamRipple
import com.ma.streamview.android.components.material.slider.StreamSlider
import com.ma.streamview.android.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val VIDEO_PLAYER_TAG = "VIDEO_PLAYER_TAG"
private const val WAIT_FOR_INTERACTION = 2_000L

@SuppressLint("SuspiciousIndentation")
@OptIn(UnstableApi::class)
@Composable
fun StreamVideoPlayer(
    modifier: Modifier = Modifier,
    onPlayPauseClick: () -> Unit = {},
    currentPosition: Long,
    onPositionChanged: (Float) -> Unit,
    updatePositions: () -> Unit,
    totalPosition: Long,
    playerView: PlayerView,
    onBackwardClick: () -> Unit,
    onForwardClick: () -> Unit,
    isPlaying: Boolean,
    isSubOnly: Boolean,
    isLoading: Boolean,
    isLive: Boolean,
) {
    var itemsVisibility by remember { mutableStateOf(true) }
    var lastInteractionTime by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(currentPosition) {
        while (true) {
            delay(1000)
            updatePositions.invoke()
        }
    }

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Coroutine to hide items after 500 milliseconds of inactivity
    LaunchedEffect(itemsVisibility) {
        while (itemsVisibility) {
            delay(WAIT_FOR_INTERACTION)
            if (System.currentTimeMillis() - lastInteractionTime >= WAIT_FOR_INTERACTION) {
                itemsVisibility = false
            }
        }
    }
    Box(
        modifier = modifier
    ) {

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = { _ ->
                            println("onDoubleTap: forwardClick")
                            onForwardClick.invoke()
                        },
                        onPress = { _ ->
                            itemsVisibility = !itemsVisibility
                            lastInteractionTime = System.currentTimeMillis()
                        }
                    )
                },
            factory = { playerView },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> {
                        println("VideoPlayerLifeCycle-> ON_PAUSE")
                        it.onPause()
                        it.player?.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        println("VideoPlayerLifeCycle-> ON_RESUME")
                        it.onResume()
                        itemsVisibility = true
                        lastInteractionTime = System.currentTimeMillis()
                    }

                    else -> Unit
                }
            },
        )
        AnimatedVisibility(
            visible = itemsVisibility,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        onClick = {
                            println(VIDEO_PLAYER_TAG + "wrapper player is clicked")
                            itemsVisibility = !itemsVisibility
                            lastInteractionTime = System.currentTimeMillis()
                        },
                        indication = null,
                        interactionSource = MutableInteractionSource(),
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.2f)
                        .clickable { lastInteractionTime = System.currentTimeMillis() }
                        .align(Alignment.BottomCenter)
                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = .45f))
                        .padding(horizontal = MaterialTheme.padding.extraSmall),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isLive){
                        StreamSlider(
                            modifier = Modifier
                                .weight(1.2f),
                            value = currentPosition.toFloat(),
                            onValueChange = { position ->
                                println("VideoPlayer. onValueChangeFinished")
                                onPositionChanged.invoke(position)
                                lastInteractionTime = System.currentTimeMillis()
                            },
                            valueRange = 0f..(if (totalPosition < 0) 0L else totalPosition).toFloat()
                                .coerceIn(0f, Float.MAX_VALUE),
                            onValueChangeFinished = {
                                println("VideoPlayer. onValueChangeFinished")
                            },
                        )
                    }
                    Text(
                        modifier = Modifier
                            .weight(.6f),
                        text = "${formatTime(currentPosition)} / ${formatTime(totalPosition)}",
                        style = MaterialTheme.typography.labelMedium
                            .copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            ),
                    )
                }
                Controller(
                    modifier = Modifier.align(Alignment.Center),
                    onBackwardClick = {
                        lastInteractionTime = System.currentTimeMillis()
                        onBackwardClick.invoke()
                    },
                    onForwardClick = {
                        lastInteractionTime = System.currentTimeMillis()
                        onForwardClick.invoke()
                    },
                    isPlaying = isPlaying,
                    onPlayPauseClick = {
                        onPlayPauseClick.invoke()
                        lastInteractionTime = System.currentTimeMillis()
                    }
                )
            }
        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
        AnimatedVisibility(
            visible = isSubOnly,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.padding.medium,
                        top = MaterialTheme.padding.medium
                    )
                    .align(Alignment.TopCenter)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.onSecondary.copy(alpha = .45f))
                    .padding(MaterialTheme.padding.extraSmall),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = "THIS CONTENT IS SUB-ONLY.",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondary
                        ),
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "PLEASE WAIT TO OPEN IT ",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondary
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun Controller(
    modifier: Modifier = Modifier,
    onBackwardClick: () -> Unit,
    onForwardClick: () -> Unit,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
) {
    var isPlayingState by remember { mutableStateOf(isPlaying) }
    var rotateForward by remember { mutableStateOf(0f) }
    var rotateBackward by remember { mutableStateOf(0f) }

    val rotateForwardAnim by animateFloatAsState(
        targetValue = rotateForward,
        animationSpec = tween(durationMillis = 300)
    )
    val rotateBackwardAnim by animateFloatAsState(
        targetValue = rotateBackward,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = modifier
            .padding(horizontal = MaterialTheme.padding.medium)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = .45f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.backward_10),
                contentDescription = "backward 10 sec",
                modifier = Modifier
                    .clickable {
                        onBackwardClick.invoke()
                        rotateBackward += 360f
                    }
                    .graphicsLayer(rotationZ = rotateBackwardAnim)
            )
        }
        PlayPauseControl(
            isPlaying = isPlayingState,
            onClick = {
                onPlayPauseClick.invoke()
                isPlayingState = !isPlayingState
            }
        )
        Box(
            modifier = modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = .45f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.forward_10_24px),
                contentDescription = "forward 10 sec",
                modifier = Modifier
                    .clickable {
                        onForwardClick.invoke()
                        rotateForward += 360f
                    }
                    .graphicsLayer(rotationZ = rotateForwardAnim)
            )
        }
    }
}

@Composable
fun PlayPauseControl(modifier: Modifier = Modifier, isPlaying: Boolean, onClick: () -> Unit) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isPlaying) 180f else 0f,
        animationSpec = tween(durationMillis = 400), label = ""
    )
    Box(
        modifier = modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary.copy(alpha = .45f))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        onClick.invoke()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = isPlaying,
            transitionSpec = {
                (fadeIn(animationSpec = tween(200)) togetherWith
                        fadeOut(animationSpec = tween(200))).using(
                    SizeTransform(clip = false)
                )
            }, label = ""
        ) { targetPlaying ->
            if (targetPlaying) {
                Image(
                    painter = painterResource(id = R.drawable.pause_24px),
                    contentDescription = "Pause",
                    modifier = Modifier.rotate(rotationAngle)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.play_arrow_24px),
                    contentDescription = "Play",
                    modifier = Modifier.rotate(rotationAngle)
                )
            }
        }
    }
}


private fun formatTime(timeMs: Long): String {
    if (timeMs < 0) {
        return "00:00"
    }
    val totalSeconds = timeMs / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return if (hours > 0) {
        "%02d:%02d:%02d".format(hours, minutes, seconds)
    } else {
        "%02d:%02d".format(minutes, seconds)
    }
}