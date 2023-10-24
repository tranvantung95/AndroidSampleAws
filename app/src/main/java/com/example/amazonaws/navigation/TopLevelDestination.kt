package com.example.amazonaws.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.amazonaws.designsystem.theme.icons.AppIcons
import com.amazonaws.matches.navigation.matchesNavigationRouter
import com.amazonawsteams.teamList.navigation.teamsNavigationRoute
import com.example.amazonaws.R
import com.amazonaws.matches.R as matchesR
import com.amazonawsteams.R as teamsR

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
    val router: String
) {
    TEAMS(
        selectedIcon = AppIcons.Teams,
        unselectedIcon = AppIcons.Teams,
        iconTextId = teamsR.string.teams_text,
        titleTextId = teamsR.string.teams_text,
        router = teamsNavigationRoute
    ),
    MATCHES(
        selectedIcon = AppIcons.Soccer,
        unselectedIcon = AppIcons.Soccer,
        iconTextId = matchesR.string.matches_text,
        titleTextId = R.string.matches_text,
        router = matchesNavigationRouter
    )
}