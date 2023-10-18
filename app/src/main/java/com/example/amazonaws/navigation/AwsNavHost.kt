package com.example.amazonaws.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
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
        teamsScreen { }
    }
}