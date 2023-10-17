package com.example.amazonaws.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.amazonaws.designsystem.theme.icons.AppIcons
import com.example.amazonaws.R
import com.amazonaws.matches.R as matchesR

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int
) {
    MATCHES(
        selectedIcon = AppIcons.Soccer,
        unselectedIcon = AppIcons.UpcomingBorder,
        iconTextId = matchesR.string.matches_text,
        titleTextId = R.string.app_name
    ),
    TEAMS(
        selectedIcon = AppIcons.Teams,
        unselectedIcon = AppIcons.UpcomingBorder,
        iconTextId = matchesR.string.matches_text,
        titleTextId = R.string.app_name
    )
}