package com.amazonawsteams.teamList.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.amazonawsteams.teamList.TeamsNestedRouter
import com.amazonawsteams.teamList.TeamsRouter

const val teamsNavigationRoute = "teams"
const val teamsNestedNavigationRoute = "teams/nested_route"
const val teams_detail_graph = "teams_detail_graph"
fun NavController.navigationToTeams(navOptions: NavOptions? = null) {
    this.navigate(teamsNavigationRoute, navOptions)
}

fun NavController.navigationToNestedTeams(navOptions: NavOptions? = null) {
    this.navigate(route = teamsNestedNavigationRoute, navOptions = navOptions)
}

fun NavGraphBuilder.teamsScreen(onTeamClick: (String) -> Unit) {
    composable(
        route = teamsNavigationRoute,
    ) {
        TeamsRouter(onTeamClick = onTeamClick)
    }
}

fun NavGraphBuilder.teamsNestedScreen(goToMatchesDetail: () -> Unit) {
    navigation(route = teams_detail_graph, startDestination = teamsNestedNavigationRoute) {
        composable(
            route = teamsNestedNavigationRoute,
        ) {
            TeamsNestedRouter(goToMatchesDetail)
        }
    }
}