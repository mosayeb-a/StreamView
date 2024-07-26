package com.ma.streamview.android.feature.list.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ma.streamview.android.Route
import com.ma.streamview.android.feature.list.ListScreen
import com.ma.streamview.android.feature.list.ListViewModel
import com.ma.streamview.android.feature.player.navigation.navigateToPlayer

const val categoryIdArg = "category_id"

fun NavController.navigateToList(
    categoryId: String? = null,
) {
    this.navigate("${Route.LIST}/{$categoryId}")
}

fun NavGraphBuilder.listScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    composable(
        route = "${Route.LIST}/{$categoryIdArg}"
    ) { navBackStackEntry ->
        val viewModel: ListViewModel = hiltViewModel(navBackStackEntry)
        ListScreen(
            viewModel = viewModel,
            snackbarHostState = snackbarHostState,
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
            onUserClicked = { userId ->
                navController.navigate(Route.PROFILE + "/$userId")
            }
        )
    }
}