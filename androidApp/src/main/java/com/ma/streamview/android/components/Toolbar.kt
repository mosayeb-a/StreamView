package com.ma.streamview.android.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ma.streamview.android.R
import com.ma.streamview.android.components.util.setRippleEffectOnClick
import com.ma.streamview.android.feature.home.formatNumberWithK
import com.ma.streamview.android.feature.search.navigation.navigateToSearch
import com.ma.streamview.android.theme.White

val TOOLBAR_SIZE = 56.dp

@Composable
fun StreamToolbar(
    modifier: Modifier = Modifier,
    shouldSearch: Boolean = false,
    shouldBack: Boolean = false,
    navController: NavController?,
    onBackClick: () -> Unit = {},
    shouldShowTitle: Boolean = false,
    title: String? = null,
    alpha: Float = 1f,
    followerCount: Int? = null
) {
    println("profileViewmodelTag. title: $title")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .alpha(alpha)
            .shadow(elevation = 4.dp)
            .background(White)
//            .padding(start = 16.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (shouldBack) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .setRippleEffectOnClick(bounded = false) {
                        navController?.popBackStack()
                        onBackClick.invoke()
                    },
                painter = painterResource(id = R.drawable.round_arrow_back_24),
                contentDescription = "back",
                contentScale = ContentScale.Inside
            )
        }
        if (shouldShowTitle) {
            Column {
                Text(
                    text = title.toString(),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = if (followerCount == 0) "0 follower"
                    else "${formatNumberWithK(followerCount!!)} followers",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = .8f)
                    ),
                )
            }
        }
        if (shouldSearch) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .setRippleEffectOnClick(bounded = false) { navController?.navigateToSearch() },
                painter = painterResource(id = R.drawable.round_search_24),
                contentDescription = "search",
                contentScale = ContentScale.Inside
            )
        }
    }
}
//    Row(
//        modifier = Modifier
//            .padding(horizontal = 16.dp)
//            .fillMaxWidth()
//            .height(56.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        if (shouldBack) {
//            Image(
//                modifier = Modifier
//                    .width(48.dp)
//                    .height(48.dp)
//                    .setRippleEffectOnClick { },
//                painter = painterResource(id = R.drawable.round_search_24),
//                contentDescription = "back"
//            )
//        }
//        if (shouldSearch) {
//            Image(
//                modifier = Modifier
//                    .width(48.dp)
//                    .height(48.dp)
//                    .setRippleEffectOnClick { },
//                painter = painterResource(id = R.drawable.round_search_24),
//                contentDescription = "search"
//            )
//        }
//    }