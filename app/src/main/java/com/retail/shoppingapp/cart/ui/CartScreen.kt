package com.retail.shoppingapp.cart.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.retail.shoppingapp.cart.viewmodel.CartViewModel
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.ui.theme.Red
import com.retail.shoppingapp.utils.ShowSnackBar
import com.retail.shoppingapp.widgets.DrawTopAppBar
import com.retail.shoppingapp.widgets.MySnackBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel,
    showSnackBar: ShowSnackBar
) {

    val snackBarState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val getCartState = viewModel.getCartState.collectAsState()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { DrawCartPageTopAppBar(scrollBehavior, navController) },
        snackbarHost = { MySnackBar(snackBarHostState = snackBarState) },
    ) {
        Box(modifier = Modifier.padding(it)) {
            if(viewModel.isEmpty.collectAsState().value) EmptyCartErrorView()
            else CartScreenContent(getCartState, viewModel)
        }
    }
}

@Composable
fun EmptyCartErrorView() {
    Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Cart is Empty", color = Red)
    }
}

@Composable
fun CartScreenContent(getCartState: State<List<Product>>, viewModel: CartViewModel) {
    LazyColumn {
        items(getCartState.value.size) {
            ItemCardView(product = getCartState.value[it]) { product ->
                viewModel.deleteItem(product)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawCartPageTopAppBar(scrollBehavior: TopAppBarScrollBehavior, navController: NavController) {
    DrawTopAppBar(
        scrollBehavior = scrollBehavior,
        title = "",
        navigationIcon = Icons.Default.KeyboardArrowLeft,
        actionIcon = null,
        navOnClick = { navController.popBackStack() },
        actionOnClick = { }
    )
}
