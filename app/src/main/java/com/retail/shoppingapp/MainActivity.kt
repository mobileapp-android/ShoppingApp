package com.retail.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.retail.shoppingapp.navigation.ScreenNavigation
import com.retail.shoppingapp.ui.theme.ShoppingAppTheme
import com.retail.shoppingapp.widgets.MySnackBar
import com.retail.shoppingapp.state.rememberAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberAppState()
            ShoppingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        snackbarHost = { MySnackBar(snackBarHostState = appState.snackBarHostState) }
                    ) { padding ->
                        Column(modifier = Modifier.padding(padding)) {
                            ScreenNavigation(
                                navHostController = appState.navController,
                                showSnackBar = { message, actionLabel, duration ->
                                    appState.showSnackBar(
                                        message,
                                        actionLabel,
                                        duration
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
