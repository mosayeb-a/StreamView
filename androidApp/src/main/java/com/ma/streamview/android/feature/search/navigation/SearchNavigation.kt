package com.ma.streamview.android.feature.search.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.ma.streamview.android.Route
import com.ma.streamview.android.feature.search.SearchScreen
import com.ma.streamview.android.feature.search.SearchViewModel
import com.ma.streamview.android.feature.player.navigation.navigateToPlayer
import com.ma.streamview.android.feature.profile.navigation.navigateToProfile


fun NavController.navigateToSearch(builder: NavOptionsBuilder.() -> Unit = {}) {
    this.navigate(Route.SEARCH) { builder.invoke(this) }
}

fun NavGraphBuilder.searchScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavHostController
) {
    composable(
        route = Route.SEARCH
    ) { navBackStackEntry ->
        val viewModel: SearchViewModel = hiltViewModel(navBackStackEntry)
        SearchScreen(
            viewModel = viewModel,
            snackbarHostState = snackbarHostState,
            onVideoClick = { id, url, slug, logo, userId, userName, description, tags ->
                println("tagNav. Navigating to player with:")
                println("tagNav. Player ID: $id")
                println("tagNav. Player URL: $url")
                println("tagNav. Slug Name: $slug")
                println("tagNav. Channel Logo: $logo")
                println("tagNav. User ID: $userId")
                println("tagNav. User Name: $userName")
                println("tagNav. Description: $description")
                println("tagNav. Tags: $tags")

                navController.navigateToPlayer(
                    playerId = id,
                    playerUrl = url,
                    slugName = slug,
                    channelLogo = logo,
                    userId = userId,
                    userName = userName,
                    title = description,
                    tags = tags,
                    false
                )
            },
            onUserClicked = { id, login ->
                println("tagNavUser id: $login")
                navController.navigateToProfile(id,login)
            },
            navController = navController,
            onStreamClick = { id, url,slug, logo, userId, userName, description, tags ->
                navController.navigateToPlayer(
                    playerId = id,
                    playerUrl = url,
                    slugName = slug,
                    channelLogo = logo,
                    userId = userId,
                    userName = userName,
                    title = description,
                    tags = tags,
                    isStream = true
                )
            },

        )
    }
}
