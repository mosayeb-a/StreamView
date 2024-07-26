package com.ma.streamview.android.feature.home.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ma.streamview.android.Route
import com.ma.streamview.android.feature.home.HomeScreen
import com.ma.streamview.android.feature.home.HomeViewModel
import com.ma.streamview.android.feature.player.navigation.navigateToPlayer
import com.ma.streamview.android.feature.profile.navigation.navigateToProfile


fun NavGraphBuilder.homeScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    composable(
        route = Route.HOME
    ) { navBackStackEntry ->
        val viewModel: HomeViewModel = hiltViewModel(navBackStackEntry)
        HomeScreen(
            onVideoClick = { id, url, slug, logo, userId, userName, description, tags ->
                navController.navigateToPlayer(
                    playerId = id,
                    playerUrl = url,
                    slugName = slug,
                    channelLogo = logo,
                    userId = userId,
                    userName = userName,
                    title = description,
                    tags = tags
                )
            },
            viewModel = viewModel,
            snackbarHostState = snackbarHostState,
            onCategoryClick = { categoryId ->
                navController.navigate(Route.LIST + "/$categoryId")
            },
            navController = navController,
            onUserClick = { id: String, login: String ->
                navController.navigateToProfile(id,login)
            },

            )
    }
}