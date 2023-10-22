package com.amazonawsteams.teamList.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.amazonawsteams.teamList.TeamsRouter

const val teamsNavigationRoute = "teams_route/"
fun NavController.navigationToTeams(navOptions: NavOptions? = null) {
    this.navigate(teamsNavigationRoute, navOptions)
}

fun NavGraphBuilder.teamsScreen(onTeamClick: (String) -> Unit) {
    composable(
        route = teamsNavigationRoute,
    ) {
        TeamsRouter(onTeamClick = onTeamClick)
    }
}