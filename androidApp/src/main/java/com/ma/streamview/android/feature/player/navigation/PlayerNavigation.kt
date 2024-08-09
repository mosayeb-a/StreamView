package com.ma.streamview.android.feature.player.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.SnackbarHostState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ma.streamview.android.Route
import com.ma.streamview.android.feature.player.PlayerScreen
import com.ma.streamview.android.feature.player.PlayerViewmodel
import com.ma.streamview.android.feature.profile.navigation.navigateToProfile
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


const val PLAYER_ID = "player_id"
const val PLAYER_URL = "player_url"
const val SLUG_NAME = "slug_name"
const val CHANNEL_LOGO = "channel_logo"
const val USER_ID = "user_id"
const val USERNAME = "username"
const val TITLE = "title"
const val TAGS_KEY = "tags"
const val IS_STREAM = "isStream"


@SuppressLint("SuspiciousIndentation")
fun NavController.navigateToPlayer(
    playerId: String,
    playerUrl: String,
    slugName: String,
    channelLogo: String,
    userId: String,
    userName: String,
    title: String,
    tags: List<String>? = null,
    isStream: Boolean,
) {
    // player URL can be null
    val encodedUrl =
        URLEncoder.encode(playerUrl, StandardCharsets.UTF_8.toString())
    val encodedLogoUrl =
        URLEncoder.encode(channelLogo, StandardCharsets.UTF_8.toString())
    // title might have "/" so it cause crash
    val encodedTitle =
        URLEncoder.encode(title, StandardCharsets.UTF_8.toString())
    val encodedSlugName =
        URLEncoder.encode(slugName, StandardCharsets.UTF_8.toString())

    this.navigate(
        "${Route.PLAYER}/$playerId/$encodedUrl/$encodedSlugName/$encodedLogoUrl/" +
                "$userId/$userName/$encodedTitle/$tags/$isStream"
    )
}

fun NavGraphBuilder.playerScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    //rldyy
    composable(
        route = "${Route.PLAYER}/{$PLAYER_ID}/{$PLAYER_URL}/{$SLUG_NAME}" +
                "/{$CHANNEL_LOGO}/{$USER_ID}/{$USERNAME}/{$TITLE}/{$TAGS_KEY}/{$IS_STREAM}",
        arguments = listOf(
            navArgument(PLAYER_ID) {
                type = NavType.StringType
            },
            navArgument(PLAYER_URL) {
                type = NavType.StringType
            },
            navArgument(SLUG_NAME) {
                type = NavType.StringType
            },
            navArgument(CHANNEL_LOGO) {
                type = NavType.StringType
            },
            navArgument(USER_ID) {
                type = NavType.StringType
            },
            navArgument(USERNAME) {
                type = NavType.StringType
            },
            navArgument(TITLE) {
                type = NavType.StringType
            },
            navArgument(TAGS_KEY) {
                type = NavType.StringArrayType
            },
            navArgument(IS_STREAM) {
                type = NavType.BoolType
            },
        ),
        enterTransition = {
            fadeIn(
                animationSpec = tween(300, easing = LinearEasing)
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) { navBackStackEntry ->
        val profilePic = navBackStackEntry.arguments?.getString(CHANNEL_LOGO)
        val userId = navBackStackEntry.arguments?.getString(USER_ID)
        val userName = navBackStackEntry.arguments?.getString(USERNAME)
        val slugName = navBackStackEntry.arguments?.getString(SLUG_NAME)
        val tags = navBackStackEntry.arguments?.getStringArrayList(TAGS_KEY)
        val title = navBackStackEntry.arguments?.getString(TITLE)
        val isStream = navBackStackEntry.arguments?.getBoolean(IS_STREAM)
        println("islive: isStream = navBackStackEntry= $isStream" )

        val viewModel: PlayerViewmodel = hiltViewModel(navBackStackEntry)
        PlayerScreen(
            viewModel = viewModel,
            profilePic = profilePic.toString(),
            userId = userId.toString(),
            username = userName.toString(),
            onUserClicked = {
                navController.navigateToProfile(
                    userId.toString(),
                    userName.toString()
                )
            },
            categoryName = slugName.toString(),
            tags = tags,
            title = title.toString(),
            navController = navController,
            isStream = isStream ?: false
        )
    }
}