package com.retail.shoppingapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.retail.shoppingapp.cart.ui.CartScreen
import com.retail.shoppingapp.cart.viewmodel.CartViewModel
import com.retail.shoppingapp.home.ui.HomeScreen
import com.retail.shoppingapp.home.viewmodel.HomeViewModel
import com.retail.shoppingapp.product.ui.ProductDetailScreen
import com.retail.shoppingapp.product.viewmodel.ProductViewModel
import com.retail.shoppingapp.splash.SplashScreen
import com.retail.shoppingapp.utils.Constants.PRODUCT_ID
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

        composable(
            route = Screen.ProductDetailScreen.route.plus("/{$PRODUCT_ID}"),
            arguments = listOf(
                navArgument(PRODUCT_ID) {
                    type = NavType.IntType
                    defaultValue = 0
                },
            )
        ) { entry ->
            GotoProductDetailScreen(navHostController, entry, showSnackBar)
        }

        composable(
            route = Screen.CartScreen.route
        ) { entry ->
            GotoCartScreen(navHostController, entry, showSnackBar)
        }
    }
}

@Composable
fun GotoCartScreen(
    navController: NavController,
    entry: NavBackStackEntry,
    showSnackBar: ShowSnackBar
) {
    val viewModel = hiltViewModel<CartViewModel>()
    CartScreen(navController = navController, viewModel = viewModel, showSnackBar= showSnackBar)
}

@Composable
fun GotoProductDetailScreen(
    navController: NavController,
    entry: NavBackStackEntry,
    showSnackBar: ShowSnackBar
) {
    val productId = entry.arguments?.getInt(PRODUCT_ID)
    val viewModel = hiltViewModel<ProductViewModel>()
    ProductDetailScreen(viewModel, navController, showSnackBar, productId)
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
    val viewModel = hiltViewModel<HomeViewModel>()
    HomeScreen(navController, viewModel, showSnackBar)
}