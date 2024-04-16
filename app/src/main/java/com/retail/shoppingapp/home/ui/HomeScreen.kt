package com.retail.shoppingapp.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.retail.shoppingapp.R
import com.retail.shoppingapp.home.viewmodel.HomeViewModel
import com.retail.shoppingapp.navigation.Screen
import com.retail.shoppingapp.ui.theme.LightGray
import com.retail.shoppingapp.ui.theme.Red
import com.retail.shoppingapp.utils.ShowSnackBar
import com.retail.shoppingapp.utils.onClick
import com.retail.shoppingapp.widgets.DrawTopAppBar
import com.retail.shoppingapp.widgets.MySnackBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: HomeViewModel,
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
            DrawHomeContent(navController, viewModel)
        }
    }
}

@Composable
fun DrawHomeContent(
    navController: NavController,
    viewModel: HomeViewModel,
) {
    val productPages = viewModel.productState.collectAsLazyPagingItems()
    LazyVerticalGrid(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)),
        columns = GridCells.Fixed(2)
    ) {
        items(productPages.itemCount) { index ->
            productPages[index]?.let { product ->
                ItemCardView(product = product) { id ->
                    navController.navigate(Screen.ProductDetailScreen.route.plus("/${id}"))
                }
            }
        }
        productPages.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { ItemLoadingView() }
                }

                loadState.refresh is LoadState.Error -> {
                    //val error = moviePagingItems.loadState.refresh as LoadState.Error
                    item {
                        ItemLoadErrorView(
                            modifier = Modifier.fillMaxWidth(),
                            message = stringResource(id = R.string.error_get_products)
                        ) { viewModel.getProductsList() }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { ItemLoadingView() }
                }

                loadState.append is LoadState.Error -> {
                    //val error = moviePagingItems.loadState.append as LoadState.Error
                    item {
                        ItemLoadErrorView(
                            modifier = Modifier.fillMaxWidth(),
                            message = stringResource(id = R.string.error_get_products)
                        ) { viewModel.getProductsList() }
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemLoadingView() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = LightGray)
    }
}

@Composable
private fun ItemLoadErrorView(modifier: Modifier, message: String?, retry: onClick) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = message.toString(), color = Red, textAlign = TextAlign.Center)
        TextButton(modifier = Modifier, onClick = { retry.invoke() }) {
            Text(text = stringResource(id = R.string.retry))
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
        actionOnClick = { navController.navigate(Screen.CartScreen.route) },
        navOnClick = { }
    )
}