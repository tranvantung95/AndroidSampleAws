package com.example.amazonaws.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.amazonaws.matches.navigation.matchesScreen
import com.amazonaws.matches.navigation.navigationToVideoPlay
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

        }
        matchesScreen { videoUrl ->
            navController.navigationToVideoPlay(navOptions = null, videoUrl)
        }
    }
}