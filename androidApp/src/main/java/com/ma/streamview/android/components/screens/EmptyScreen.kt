package com.ma.streamview.android.components.screens

import StreamTextButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ma.streamview.android.components.screens.navigation.navigateToEmptyScreen
import kotlin.random.Random


//@Composable
//private fun HomeCategoryTabs(
//    categories: List<HomeCategory>,
//    selectedCategory: HomeCategory,
//    onCategorySelected: (HomeCategory) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
//    val indicator = @Composable { tabPositions: List<TabPosition> ->
//        TabIndicator(
//            Modifier.tabIndicatorOffset(tabPositions[selectedIndex])
//        )
//    }
//
//    TabRow(
//        selectedTabIndex = selectedIndex,
//        indicator = indicator,
//        modifier = modifier
//    ) {
//        categories.forEachIndexed { index, category ->
//            Tab(
//                selected = index == selectedIndex,
//                onClick = { onCategorySelected(category) },
//                text = {
//                    Text(
//                        text = when (category) {
//                            HomeCategory.Library -> stringResource(R.string.home_library)
//                            HomeCategory.Discover -> stringResource(R.string.home_discover)
//                        },
//                        style = MaterialTheme.typography.body
//                    )
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun ScreenA() {
//
//}
//@Composable
//fun ScreenB() {
//
//}
data class EmptyScreenAction(
    val hint: String,
    val onClick: () -> Unit,
)

@Composable
fun TabIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Spacer(
        modifier
            .padding(horizontal = 24.dp)
            .height(4.dp)
            .background(color, RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
    )
}

@Composable
fun EmptyScreen(
    message: String,
    modifier: Modifier = Modifier,
    action: EmptyScreenAction? = null,
) {
    val face = remember { getRandomErrorFace() }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Text(
                text = face,
                style = MaterialTheme.typography.displayMedium
                    .copy(color = MaterialTheme.colorScheme.secondary.copy(alpha = .8f)),
            )
        }
        Text(
            text = message,
            modifier = Modifier
                .padding(top = 24.dp),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        if (action != null) {
            StreamTextButton(
                modifier = Modifier
                    .padding(start = 98.dp, end = 98.dp, top = 12.dp),
                onClick = { action.onClick.invoke() },
                text = action.hint
            )
        }
    }
}

@Composable
fun EmptyScreen(
    message: String,
    modifier: Modifier = Modifier,
    action: EmptyScreenAction? = null,
    navController: NavController,
    shouldShowBack: Boolean = false
) {
    val face = remember { getRandomErrorFace() }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Text(
                text = face,
                style = MaterialTheme.typography.displayMedium
                    .copy(color = MaterialTheme.colorScheme.secondary.copy(alpha = .8f)),
            )
        }

        Text(
            text = message,
            modifier = Modifier
                .padding(top = 24.dp),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        when {
            action != null -> {
                StreamTextButton(
                    modifier = Modifier
                        .padding(start = 98.dp, end = 98.dp, top = 12.dp),
                    onClick = { action.onClick.invoke() },
                    text = action.hint
                )
            }

            shouldShowBack || message.contains("unknown error") -> {
                StreamTextButton(
                    modifier = Modifier
                        .padding(start = 98.dp, end = 98.dp, top = 12.dp),
                    onClick = { navController.popBackStack() },
                    text = "Back"
                )
            }
        }
    }
}

private val ErrorFaces = listOf(
    "(･o･;)",
    "Σ(ಠ_ಠ)",
    "ಥ_ಥ",
    "(˘･_･˘)",
    "(；￣Д￣)",
    "(･Д･。",
)

private fun getRandomErrorFace(): String {
    return ErrorFaces[Random.nextInt(ErrorFaces.size)]
}
