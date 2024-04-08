package com.retail.shoppingapp.navigation

sealed class Screen(val route: String) {
    data object SplashScreen : Screen("splash")
    data object HomeScreen : Screen("home")
}