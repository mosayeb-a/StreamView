package com.ma.streamview.android.feature.following.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ma.streamview.android.Route
import com.ma.streamview.android.feature.following.FollowingScreen
import com.ma.streamview.android.feature.following.FollowingViewModel
import com.ma.streamview.android.feature.profile.navigation.navigateToProfile

fun NavController.navigateToFollowing() {
    this.navigate(Route.FOLLOWING)
}

fun NavGraphBuilder.followingScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    composable(
        route = Route.FOLLOWING
    ) { navBackStackEntry ->
        val viewModel: FollowingViewModel = hiltViewModel(navBackStackEntry)
        FollowingScreen(
            viewModel = viewModel,
            snackbarHostState = snackbarHostState,
            onUserClicked = { id,login ->
                navController.navigateToProfile(id,login)
            },
            navController = navController
        )
    }
}