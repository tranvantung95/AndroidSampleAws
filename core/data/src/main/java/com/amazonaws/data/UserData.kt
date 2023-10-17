package com.amazonaws.data

/**
 * app config user
 */
data class UserData(
    val darkThemeConfig: DarkThemeConfig,
    val themeBrand: ThemeBrand,
    val useDynamicColor: Boolean,
) {
}