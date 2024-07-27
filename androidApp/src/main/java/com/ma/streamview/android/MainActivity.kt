package com.ma.streamview.android

import StreamAppState
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ma.streamview.android.common.connectivityState
import com.ma.streamview.android.components.material.SecondrySnackbar
import com.ma.streamview.android.components.screens.EmptyScreen
import com.ma.streamview.android.components.screens.EmptyScreenAction
import com.ma.streamview.android.components.screens.LoadingScreen
import com.ma.streamview.android.components.screens.navigation.navigateToEmptyScreen
import com.ma.streamview.android.theme.StreamTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import rememberStreamAppState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StreamTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()
                val isOnline by connectivityState()
                val shouldShowBottomBar = shouldShowBottomBar(navController, isOnline)
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    snackbarHost = {
                        SecondrySnackbar(hostState = snackbarHostState)
                    },
                    bottomBar = {
                        AnimatedVisibility(
                            visible = shouldShowBottomBar,
                            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                        ) {
                            BottomNavBar(navController = navController)
                        }
                    }
                ) {
                    StreamApp(
                        snackbarHostState = snackbarHostState,
                        navController = navController,
                        modifier = Modifier
                            .padding(bottom = if (shouldShowBottomBar) 56.dp else 0.dp)
                            .background(Color.White),
                        isOnline = isOnline
                    )
                }
            }
        }
    }
}

@Composable
fun shouldShowBottomBar(navController: NavHostController, isOnline: Boolean): Boolean {
    val currentDestination by navController.currentBackStackEntryAsState()
    return isOnline && when (currentDestination?.destination?.route?.substringBefore('/')) {
        Route.PLAYER -> false
//        Route.PROFILE -> false
        Route.EMPTY_SCREEN -> false
        else -> true
    }
}


//   val alpha = if (pagerState.currentPage == tabIndex) {
//                        1f - pagerState.currentPageOffsetFraction.absoluteValue
//                    } else if (pagerState.currentPage + 1 == tabIndex) {
//                        pagerState.currentPageOffsetFraction.absoluteValue
//                    } else {
//                        1f
//                    }
