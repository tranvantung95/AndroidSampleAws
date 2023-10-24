package com.example.amazonaws.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.amazonaws.matches.navigation.matchesNavigationRouter
import com.amazonaws.matches.navigation.navigationToMatches
import com.amazonawsteams.teamList.navigation.navigationToTeams
import com.amazonawsteams.teamList.navigation.teamsNavigationRoute
import com.example.amazonaws.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): AppState {
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
    ) {
        AppState(
            navController,
            coroutineScope,
            windowSizeClass
        )
    }
}

@Stable
class AppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass
) {
    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    private val routes = TopLevelDestination.values().map { it.router }

    val hideBottomBarWithNestedScreen: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route in routes

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            teamsNavigationRoute -> TopLevelDestination.TEAMS
            matchesNavigationRouter -> TopLevelDestination.MATCHES
            else -> {
                null
            }

        }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
        when (topLevelDestination) {
            TopLevelDestination.TEAMS -> navController.navigationToTeams(topLevelNavOptions)
            TopLevelDestination.MATCHES -> navController.navigationToMatches(topLevelNavOptions)
            else -> {

            }
        }
    }
}
