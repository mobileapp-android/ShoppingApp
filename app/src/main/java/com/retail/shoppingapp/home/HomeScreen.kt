package com.retail.shoppingapp.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.retail.shoppingapp.R
import com.retail.shoppingapp.utils.ShowSnackBar
import com.retail.shoppingapp.widgets.DrawTopAppBar
import com.retail.shoppingapp.widgets.MySnackBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    showSnackBar: ShowSnackBar = { _, _, _ -> },
) {
    val snackBarState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DrawHomeTopAppBar(
                navController = navController,
                scrollBehavior = scrollBehavior,
            )
        },
        snackbarHost = { MySnackBar(snackBarHostState = snackBarState) },
    ) {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawHomeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
) {
    DrawTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = Icons.Default.Info,
        title = stringResource(id = R.string.app_name),
        actionIcon = Icons.Default.ShoppingCart,
        actionOnClick = {  },
        navOnClick = { }
    )
}