package com.retail.shoppingapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.retail.shoppingapp.home.HomeScreen
import com.retail.shoppingapp.splash.SplashScreen
import com.retail.shoppingapp.utils.ShowSnackBar

@Composable
fun ScreenNavigation(
    navHostController: NavHostController,
    showSnackBar: ShowSnackBar
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(route = Screen.SplashScreen.route) {
            NavigateToSplashScreen(navHostController)
        }

        composable(route = Screen.HomeScreen.route) {
            GotoHomeScreen(navHostController, showSnackBar)
        }
    }
}

@Composable
fun NavigateToSplashScreen(navController: NavController) {
    SplashScreen(navController)
}

@Composable
fun GotoHomeScreen(
    navController: NavController,
    showSnackBar: ShowSnackBar
) {
    HomeScreen(navController, showSnackBar)
}