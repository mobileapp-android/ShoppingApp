package com.retail.shoppingapp.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.retail.shoppingapp.navigation.Screen
import com.retail.shoppingapp.ui.theme.EasyCartTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController = rememberNavController()) {

    LaunchedEffect(Unit) {
        delay(2000)
        gotoHomeScree(navController)
    }
    SplashScreenContent()
}

fun gotoHomeScree(navController: NavController) {
    navController.navigate(Screen.HomeScreen.route){
        popUpTo(Screen.SplashScreen.route) { inclusive = true }
    }
}

@Composable
private fun SplashScreenContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Shopping App", fontSize = 25.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    EasyCartTheme {
        SplashScreen()
    }
}