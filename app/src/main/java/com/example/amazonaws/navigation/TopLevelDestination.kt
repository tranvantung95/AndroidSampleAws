package com.example.amazonaws.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.amazonaws.designsystem.theme.icons.AppIcons
import com.example.amazonaws.R
import com.amazonaws.matches.R as matchesR
import com.amazonawsteams.R as teamsR

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int
) {
    TEAMS(
        selectedIcon = AppIcons.Teams,
        unselectedIcon = AppIcons.Teams,
        iconTextId = teamsR.string.teams_text,
        titleTextId = R.string.app_name
    ),
    MATCHES(
        selectedIcon = AppIcons.Soccer,
        unselectedIcon = AppIcons.Soccer,
        iconTextId = matchesR.string.matches_text,
        titleTextId = R.string.app_name
    )
}