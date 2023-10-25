package com.amazonaws.matches.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.amazonaws.matches.MatchesNestedRouter
import com.amazonaws.matches.MatchesRouter
import com.amazonaws.matches.VideoPlayerScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

const val matchesNavigationRouter = "matches?teamId={teamId}"
const val matchesNestedNavigationRouter = "matches/nested_router"
const val matchesVideoNavigationRouter = "matches_video_player/{VIDEO_URL}"
const val matches_detail_graph = "matches_detail_graph"

fun NavController.navigationToMatches(navOptions: NavOptions? = null, teamId: String? = null) {
    this.navigate(
        matchesNavigationRouter.replace("{teamId}", teamId.orEmpty()),
        navOptions = navOptions
    )
}

fun NavController.navigationToNestedMatches(
    navOptions: NavOptions? = null
) {
    this.navigate(
        matchesNestedNavigationRouter,
        navOptions = navOptions
    )
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

fun NavGraphBuilder.matchesScreen(
    onHighlightClick: (String) -> Unit,
    onDetailClick: () -> Unit
) {
    composable(
        route = matchesNavigationRouter,
        arguments = listOf(navArgument("teamId") {
            type = NavType.StringType
            defaultValue = null
            nullable = true
        })
    ) { backStackEntry ->
        MatchesRouter(
            onHighlightClick = onHighlightClick,
            teamId = backStackEntry.arguments?.getString("teamId"),
            onDetailClick = onDetailClick
        )
    }
}

fun NavGraphBuilder.matchesNestedScreen(navController: NavController) {
    navigation(route = matches_detail_graph, startDestination = matchesNestedNavigationRouter) {
        composable(
            route = matchesNestedNavigationRouter
        ) {
            MatchesNestedRouter()
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

}
