package com.ma.streamview.android.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableTabRow(
    modifier: Modifier = Modifier,
    tabsList: List<String>,
    showDivider: Boolean = false,
    onTabSelected: @Composable (page: Int) -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = { tabsList.size },
        initialPage = 0,
    )
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        ScrollableTabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = selectedTabIndex.value,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
//                val currentPageOffsetFraction = pagerState.currentPageOffsetFraction
//                val currentTab = selectedTabIndex.value
//                val nextTab = if (currentPageOffsetFraction > 0) {
//                    (currentTab + 1) % tabsList.size
//                } else {
//                    (currentTab - 1).coerceAtLeast(0)
//                }
//
//                val indicatorStart = lerp(
//                    tabPositions[currentTab].left,
//                    tabPositions[nextTab].left,
//                    currentPageOffsetFraction.absoluteValue
//                )
//                val indicatorEnd = lerp(
//                    tabPositions[currentTab].right,
//                    tabPositions[nextTab].right,
//                    currentPageOffsetFraction.absoluteValue
//                )
                val currentPageOffsetFraction = pagerState.currentPageOffsetFraction
                val currentTab = selectedTabIndex.value
                val nextTab = if (currentPageOffsetFraction > 0) {
                    (currentTab + 1) % tabsList.size
                } else {
                    (currentTab - 1).coerceAtLeast(0)
                }

                val startTabLeft = tabPositions[currentTab].left
                val startTabRight = tabPositions[currentTab].right
                val endTabLeft = tabPositions[nextTab].left
                val endTabRight = tabPositions[nextTab].right

                val indicatorStart = lerp(startTabLeft, endTabLeft, currentPageOffsetFraction.absoluteValue)
                val indicatorEnd = lerp(startTabRight, endTabRight, currentPageOffsetFraction.absoluteValue)

                TabIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.BottomStart)
                        .offset(x = indicatorStart)
                        .width(indicatorEnd - indicatorStart),
                )
            },
            divider = {}
        ) {
            tabsList.forEachIndexed { tabIndex, tabName ->
                Tab(
                    modifier=Modifier.padding(top = 16.dp),
                    selected = tabIndex == selectedTabIndex.value,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(tabIndex)
                        }
                    },
                    text = {
                        Text(
                            text = tabName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = if (tabIndex == selectedTabIndex.value) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                     MaterialTheme.colorScheme.outlineVariant
                                }
                            )
                        )
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            onTabSelected.invoke(page)
        }
    }
}

@Composable
fun TabIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Spacer(
        modifier
            .padding(horizontal = 24.dp)
            .height(4.dp)
            .shadow(2.dp, RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
            .background(color, RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
    )
}