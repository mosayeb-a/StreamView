package com.ma.streamview.android

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


data class BottomNavItem(
    val title: String,
    val selectedIcon: Int,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

fun Modifier.tabIndicatorOffset(
    currentTabPosition: TabPosition,
    animationSpec: AnimationSpec<Dp> = spring(stiffness = Spring.StiffnessVeryLow)
): Modifier = composed {
    val currentTabWidth by animateDpAsState(
        targetValue = currentTabPosition.width,
        animationSpec = animationSpec
    )
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = animationSpec
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}



@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val items = remember {
        mutableListOf(
            BottomNavItem(
                title = Route.HOME,
                selectedIcon = R.drawable.anim_browse,
                hasNews = false,
            ),
            BottomNavItem(
                title = Route.SEARCH,
                selectedIcon = R.drawable.anim_updates_enter,
                hasNews = false,
            ),
            BottomNavItem(
                title = Route.FOLLOWING,
                selectedIcon = R.drawable.anim_library_enter,
                hasNews = false,
            ),
        )
    }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val configuration = LocalConfiguration.current
    val isLargeScreen = configuration.screenWidthDp >= 600

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(elevation = 12.dp, shape = RectangleShape, clip = false),
        verticalArrangement = Arrangement.Bottom
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                Column(
                    modifier = Modifier
                        .tabIndicatorOffset(
                            currentTabPosition = tabPositions[selectedTabIndex],
                            animationSpec = tween(
                                durationMillis = 100,
                                easing = FastOutSlowInEasing
                            )
                        )
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = if (isLargeScreen) 86.dp else 36.dp,
                                end = if (isLargeScreen) 86.dp else 36.dp,
                                bottom = 24.72.dp,
                                top = 4.27.dp
                            )
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = .3f),
                                shape = MaterialTheme.shapes.extraLarge
                            )
                    )
                }
            },
        ) {
            items.forEachIndexed { tabIndex, item ->
                NavBarItem(
                    icon = item.selectedIcon,
                    title = item.title,
                    isSelected = selectedTabIndex == tabIndex
                ) {
                    selectedTabIndex = tabIndex
                    navController.navigate(item.title) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }
}


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
    fun NavBarItem(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var atEnd by remember { mutableStateOf(isSelected) }
    LaunchedEffect(isSelected) {
        atEnd = isSelected
    }
    Column(
        modifier = modifier
            .height(56.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onClick.invoke()
                    },
                    onPress = {
//                        tryAwaitRelease()
                        onClick.invoke()
                    }
                )
            }
//            .clickable(
//                onClick = { onClick.invoke() },
//                indication = null,
//                interactionSource = MutableInteractionSource(),
//            )
            .alpha(if (isSelected) 1f else .75f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = rememberAnimatedVectorPainter(
                animatedImageVector = AnimatedImageVector.animatedVectorResource(id = icon),
                atEnd = atEnd
            ),
            contentDescription = title
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            ),
        )
    }
}
