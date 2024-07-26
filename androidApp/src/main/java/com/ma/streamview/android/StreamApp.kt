package com.ma.streamview.android

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ma.streamview.android.common.retryConnectionCheck
import com.ma.streamview.android.components.screens.OfflineScreen
import com.ma.streamview.android.components.screens.navigation.emptyScreen
import com.ma.streamview.android.feature.search.navigation.searchScreen
import com.ma.streamview.android.feature.following.navigation.followingScreen
import com.ma.streamview.android.feature.home.navigation.homeScreen
import com.ma.streamview.android.feature.list.navigation.listScreen
import com.ma.streamview.android.feature.player.navigation.playerScreen
import com.ma.streamview.android.feature.profile.navigation.profileScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun StreamApp(
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isOnline: Boolean,
) {
    val context = LocalContext.current
    var retryTriggered by remember { mutableStateOf(false) }
    var showOfflineMessage by remember { mutableStateOf(false) }
    var retrying by remember { mutableStateOf(false) }


    LaunchedEffect(retryTriggered) {
        if (retryTriggered) {
            retrying = true
            if (context.retryConnectionCheck()) {
                navController.navigate(Route.HOME) {
                    popUpTo(0)
                }
            } else {
                showOfflineMessage = true
            }
            retrying = false
            retryTriggered = false
        }
    }

    if (isOnline) {
        NavHost(
            modifier = modifier.fillMaxSize(),
            navController = navController,
            startDestination = Route.HOME,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            homeScreen(snackbarHostState, navController)
            playerScreen(snackbarHostState, navController)
            listScreen(snackbarHostState, navController)
            profileScreen(snackbarHostState, navController)
            followingScreen(snackbarHostState, navController)
            searchScreen(snackbarHostState, navController)
            emptyScreen(navController)
        }
    } else {
        OfflineScreen(
            showOfflineMessage = showOfflineMessage,
            retrying = retrying,
            onRetryClick = {
                retryTriggered = true
            }
        )
    }
}


object Route {
    const val HOME = "Home"
    const val FOLLOWING = "Following"
    const val SEARCH = "Explore"
    const val PLAYER = "player"
    const val LIST = "list"
    const val PROFILE = "profile"
    const val EMPTY_SCREEN = "empty_screen"
}