package com.ma.streamview.android.components.screens.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ma.streamview.android.Route
import com.ma.streamview.android.components.screens.EmptyScreen
import com.ma.streamview.android.feature.player.navigation.CHANNEL_LOGO

//import kotlinx.serialization.encodeToString


const val MESSAGE = "message"
fun NavController.navigateToEmptyScreen(message: String, builder: NavOptionsBuilder.() -> Unit={}) {
    this.navigate(
        "${Route.EMPTY_SCREEN}/$message"
    ) { builder.invoke(this) }
}

fun NavGraphBuilder.emptyScreen(navController: NavController) {
    composable(
        route = "${Route.EMPTY_SCREEN}/{$MESSAGE}",
        arguments = listOf(
            navArgument(MESSAGE) { type = NavType.StringType },
        ),
    ) { navBackStackEntry ->
        val message = navBackStackEntry.arguments?.getString(CHANNEL_LOGO)
            ?: "unknown error has occurred"
        EmptyScreen(message = message, navController = navController)

    }
}