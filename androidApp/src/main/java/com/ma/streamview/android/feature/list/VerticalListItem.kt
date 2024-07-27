package com.ma.streamview.android.feature.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.ma.streamview.android.common.animatedScaleOnTouch
import com.ma.basloq.android.components.material.padding
import com.ma.streamview.android.feature.home.formatNumberWithK
import com.ma.streamview.common.formatRelativeDate

@Composable
fun VerticalListItem(
    imageUrl: String,
    modifier: Modifier = Modifier,
    title: String,
    profilePic: String = "",
    username: String? = null,
    slugName: String,
    viewCount: Int,
    createdAt: String,
    onClick: () -> Unit,
    shouldShowUsername: Boolean = true
) {
    Spacer(modifier = Modifier.height(MaterialTheme.padding.small))
    Row(
        modifier = modifier
            .padding(start = MaterialTheme.padding.medium)
            .animatedScaleOnTouch { onClick.invoke() }
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Start,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                .crossfade(true).data(imageUrl).build(),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(100.dp)
                .width(160.dp),
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (shouldShowUsername) {
                Text(
                    text = username.toString(),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Text(
                text = slugName,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colorScheme.secondary.copy(
                        alpha = .8f
                    )
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row {
                Text(
                    text = "${formatNumberWithK(viewCount)} Views - ",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.W500,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary.copy(
                            alpha = .8f
                        )
                    ),
                )
                Text(
                    text = formatRelativeDate(createdAt),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.W500,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary.copy(
                            alpha = .8f
                        )
                    ),
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(MaterialTheme.padding.small))
}


@Composable
fun CategoryVerticalListItem(
    imageUrl: String,
    modifier: Modifier = Modifier,
    title: String,
    viewersCount: Int,
    onClick: () -> Unit,
) {
    Spacer(modifier = Modifier.height(MaterialTheme.padding.small))
    var imageHeight by remember { mutableStateOf(100f) }
    var imageWidth by remember { mutableStateOf(160f) }

    Row(
        modifier = modifier
            .padding(start = MaterialTheme.padding.medium)
            .animatedScaleOnTouch { onClick.invoke() }
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Start,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                .crossfade(true).data(imageUrl).build(),
            contentDescription = "Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(148.dp)
                .width(110.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
//                .padding(vertical = 8.dp),
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.padding.small))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.padding.small))
            Text(
                text = if (viewersCount == 0) "0 viewer" else "${formatNumberWithK(viewersCount)} viewers",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colorScheme.secondary.copy(
                        alpha = .8f
                    )
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

        }
    }
    Spacer(modifier = Modifier.height(MaterialTheme.padding.small))
}

