package com.healthyhour.app.presentation.navigation

sealed class NavRoutes(val route: String) {
    data object Home : NavRoutes("home")
    data object AppDetail : NavRoutes("app_detail/{packageName}") {
        fun createRoute(packageName: String) = "app_detail/$packageName"
    }
    data object History : NavRoutes("history")
    data object Settings : NavRoutes("settings")
    data object Onboarding : NavRoutes("onboarding")
}
