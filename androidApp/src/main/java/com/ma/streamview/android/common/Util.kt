package com.ma.streamview.android.common

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import com.ma.basloq.android.components.material.SecondaryItemAlpha
import com.ma.streamview.android.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit


fun Modifier.secondaryItemAlpha(): Modifier = this.alpha(SecondaryItemAlpha)


fun Long.formatMinSec(): String {
    return if (this == 0L) {
        "..."
    } else {
        String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(this),
            TimeUnit.MILLISECONDS.toSeconds(this) -
                    TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(this)
                    )
        )
    }
}

@SuppressLint("SimpleDateFormat")
fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("HH:mm:ss")
    return format.format(date)
}

fun findMostRepeatedValue(values: List<String>): String? {
    val nameCounts = mutableMapOf<String, Int>()
    for (name in values.takeLast(10)) {
        if (nameCounts.containsKey(name)) {
            nameCounts[name] = nameCounts[name]!! + 1
        } else {
            nameCounts[name] = 1
        }
    }
    return nameCounts.maxByOrNull { it.value }?.key
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.animatedScaleOnTouch(onClick: () -> Unit): Modifier = composed {
    val selected = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(if (selected.value) .88f else 1f, label = "scale_animation")
    val scope = rememberCoroutineScope()
    this
        .scale(scale.value)
        .pointerInteropFilter {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    selected.value = true
                }

                MotionEvent.ACTION_UP -> {
                    scope.launch {
                        selected.value = false
                        delay(100)
                        if(it.action ==MotionEvent.ACTION_UP ){
                        onClick()
                        }

                    }
                }

                MotionEvent.ACTION_CANCEL -> {
                    selected.value = false
                }
            }
            true
        }
}

suspend fun SnackbarHostState.showSnackbar(
    message: String,
    actionLabel: String?,
    onActionClick: () -> Unit = {}
) {
    val result = this@showSnackbar.showSnackbar(
        message = message,
        actionLabel = actionLabel,
        duration = SnackbarDuration.Indefinite
    )

    when (result) {
        SnackbarResult.ActionPerformed -> {
            onActionClick.invoke()
            println("Action button clicked")
        }

        SnackbarResult.Dismissed -> {
            println("Snackbar dismissed")
        }
    }
}

class StreamRipple(
    val color: Color,
    val alpha: RippleAlpha = RippleAlpha(
        0.34f,
        0.34f,
        0.46f,
        0.22f
    )
) : RippleTheme {
    @Composable
    override fun defaultColor() = color

    @Composable
    override fun rippleAlpha() = alpha
}