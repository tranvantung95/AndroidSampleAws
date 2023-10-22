package com.amazonaws.matches.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.amazonaws.matches.MatchesRouter
import com.amazonaws.matches.VideoPlayerScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

const val matchesNavigationRouter = "matches_router/"
const val matchesVideoNavigationRouter = "matches_router_video_player/{VIDEO_URL}"
fun NavController.navigationToMatches(navOptions: NavOptions? = null) {
    this.navigate(matchesNavigationRouter, navOptions)
}

fun NavController.navigationToVideoPlay(navOptions: NavOptions? = null, videoUrl: String) {
    this.navigate(
        matchesVideoNavigationRouter.replace(
            "{VIDEO_URL}", URLEncoder.encode(
                videoUrl,
                StandardCharsets.UTF_8.toString()
            )
        ), navOptions
    )
}

fun NavGraphBuilder.matchesScreen(onMatchesClick: (String) -> Unit) {
    composable(
        route = matchesNavigationRouter,
    ) {
        MatchesRouter(onHighlightClick = onMatchesClick)
    }

    composable(
        route = matchesVideoNavigationRouter,
        arguments = listOf(navArgument("VIDEO_URL") {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        VideoPlayerScreen(url = backStackEntry.arguments?.getString("VIDEO_URL").orEmpty())
    }
}
