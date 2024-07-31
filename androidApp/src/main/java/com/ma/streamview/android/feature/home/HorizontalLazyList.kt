package com.ma.streamview.android.feature.home

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ma.basloq.android.components.material.padding
import com.ma.streamview.android.common.animatedScaleOnTouch
import com.ma.streamview.android.theme.Black
import com.ma.streamview.android.theme.Red
import com.ma.streamview.android.feature.home.snap_to_center_behaviour.rememberSnapperFlingBehavior
import kotlinx.coroutines.delay


class SmoothSlowFlingBehavior(
    private val slowDecelerationRate: Float,
    private val fastDecelerationRate: Float,
    private val delayMillis: Long
) : FlingBehavior {
    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        var velocity = initialVelocity
        val decelerationRate =
            if (velocity > 5000f || velocity < -5000f) fastDecelerationRate else slowDecelerationRate
        while (velocity > 1f || velocity < -1f) {
            velocity *= decelerationRate

            val delta = velocity * 0.01f
            val consumed = scrollBy(delta)

            if (consumed == 0f) break
            delay(delayMillis)
        }
        return velocity
    }
}


@Composable
fun HorizontalLazyList(
    previews: List<String?>,
    viewersCount: List<Int>?,
    isStream: Boolean,
    modifier: Modifier = Modifier,
    profilePic: List<String?>,
    tags: List<List<String>?> = emptyList(),
    usernames: List<String?>,
    categoryNames: List<String>,
    titles: List<String>,
    onVideoClick: (index: Int) -> Unit,
    onUserClick: (index: Int) -> Unit,
) {
    val listState = rememberLazyListState()
    LazyRow(
        state = listState,
        flingBehavior = rememberSnapperFlingBehavior(listState),
        modifier = modifier
            .fillMaxWidth(),
    ) {
        itemsIndexed(previews, key = { _, item -> item!! }) { index, _ ->
            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .padding(
                                start = if (index == 0) 16.dp else 0.dp,
                                end = 8.5.dp
                            )
                    ) {
                        StreamImage(
                            url = previews[index].toString(),
                            modifier = modifier
//                                .animatedScaleOnTouch { onItemClickwed.invoke(index) }
                                .height(166.56.dp)
                                .width(310.dp)
                        ) {
                            onVideoClick.invoke(index)
                        }
                        StreamLabel(
                            modifier = Modifier.align(Alignment.TopStart),
                            background = Red,
                            isStream = isStream
                        )
                        ViewerLabel(
                            modifier = Modifier.align(Alignment.BottomStart),
                            background = Black,
                            viewersCount = formatNumberWithK(viewersCount?.get(index) ?: 0)
                        )
                    }
                }
                UserItem(
                    modifier = Modifier
                        .padding(top = 8.dp, start = if (index == 0) 20.dp else 4.dp, end = 8.dp),
                    profilePictureUrl = profilePic[index].toString(),
                    onClick = { onUserClick.invoke(index) },
                    username = usernames[index].toString(),
                    title = titles[index],
                    categoryName = categoryNames[index],
                    tags = tags[index],
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CategoryHorizontalList(
    modifier: Modifier = Modifier,
    previews: List<String>,
    onClick: (index: Int) -> Unit,
) {
    val listState = rememberLazyListState()
    LazyRow(
        state = listState,
        flingBehavior = rememberSnapperFlingBehavior(listState),
        modifier = modifier
            .fillMaxWidth(),
    ) {
        items(count = previews.size, key = { i -> previews[i] }) { index ->
            Box(
                modifier = Modifier
                    .padding(
                        start = if (index == 0) 16.dp else 0.dp,
                        end = 8.5.dp
                    )
            ) {
                StreamImage(
                    modifier = modifier
//                        .padding(start = MaterialTheme.padding.medium)
                        .height(148.dp)
                        .width(110.dp)
                        .animatedScaleOnTouch { onClick.invoke(index)  },
                    url = previews[index],
                )
            }
        }
    }
}