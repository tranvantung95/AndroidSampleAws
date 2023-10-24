package com.example.amazonaws.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.amazonaws.matches.navigation.matchesScreen
import com.amazonaws.matches.navigation.navigationToMatches
import com.amazonaws.matches.navigation.navigationToVideoPlay
import com.amazonaws.matches.navigation.playVideoScreen
import com.amazonawsteams.teamList.navigation.teamsNavigationRoute
import com.amazonawsteams.teamList.navigation.teamsScreen
import com.example.amazonaws.ui.AppState

@Composable
fun AwsNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
    startDestination: String = teamsNavigationRoute
) {
    val navController = appState.navController
    NavHost(navController = navController, startDestination = startDestination, modifier) {
        teamsScreen {
            navController.navigationToMatches(teamId = it)
        }
        matchesScreen { videoUrl ->
            navController.navigationToVideoPlay(navOptions = null, videoUrl)
        }
        playVideoScreen()
    }

}