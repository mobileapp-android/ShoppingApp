package com.retail.shoppingapp.product.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.retail.shoppingapp.R
import com.retail.shoppingapp.navigation.Screen
import com.retail.shoppingapp.product.viewmodel.ProductViewModel
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.ui.theme.ShoppingAppTheme
import com.retail.shoppingapp.utils.Constants.API_SUCCESS
import com.retail.shoppingapp.utils.ShowSnackBar
import com.retail.shoppingapp.utils.onClick
import com.retail.shoppingapp.widgets.DrawTopAppBar
import com.retail.shoppingapp.widgets.ImageViewPager
import com.retail.shoppingapp.widgets.MySnackBar
import com.retail.shoppingapp.widgets.ProgressDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    viewModel: ProductViewModel,
    navController: NavController,
    showSnackBar: ShowSnackBar,
    productId: Int?
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getProductByID(productId = productId)
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val productDetailsState = viewModel.productDetailsState.collectAsState()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { DrawProductPageTopAppBar(scrollBehavior, navController) },
    ) {
        Box(modifier = Modifier.padding(it)) {
            if (viewModel.isLoading.collectAsState().value)
                ProgressDialog { }
            else if (!viewModel.getProductError.collectAsState().value)
                ProductDetailsContent(productDetailsState) { product ->
                    viewModel.addToCart(product) {
                        showSnackBar.invoke(
                            "Item has been added to the cart",
                            API_SUCCESS,
                            SnackbarDuration.Short
                        )
                        navController.popBackStack()
                        navController.navigate(Screen.CartScreen.route)
                    }
                }
            if (viewModel.getProductError.collectAsState().value) ShowGetProductError {
                viewModel.getProductByID(productId = productId)
            }
        }
    }
}

@Composable
private fun ShowGetProductError(onClick: onClick) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Product details load error, please check your internet!",
                textAlign = TextAlign.Center
            )
            TextButton(onClick = { onClick.invoke() }) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
private fun ProductDetailsContent(
    productDetailsState: State<Product>,
    addToCartOnClick: (Product) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ProductImagePager(productDetailsState.value.images)
        DrawProductInfoCard(product = productDetailsState.value)
        AddToCartButton(modifier = Modifier.fillMaxWidth()) {
            addToCartOnClick.invoke(productDetailsState.value)
        }
    }
}

@Composable
fun AddToCartButton(modifier: Modifier, onClick: onClick) {
    OutlinedButton(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium)),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .height(dimensionResource(id = R.dimen.filled_button_height)),
        onClick = { onClick.invoke() },
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = colorResource(id = R.color.purple_700)
        ),
        elevation = ButtonDefaults.buttonElevation(dimensionResource(id = R.dimen.elevation_small))
    ) {
        Text(
            text = stringResource(id = R.string.add_to_cart),
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ProductImagePager(imageUrls: List<String>?) {
    if (!imageUrls.isNullOrEmpty()) ImageViewPager(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.box_size_xl)),
        imageUrls = imageUrls
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawProductPageTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
) {
    DrawTopAppBar(
        scrollBehavior = scrollBehavior,
        title = "",
        navigationIcon = Icons.Default.KeyboardArrowLeft,
        actionIcon = Icons.Default.ShoppingCart,
        navOnClick = { navController.popBackStack() },
        actionOnClick = { navController.navigate(Screen.CartScreen.route) }
    )
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailsPreview() {
    val productDetailsState = remember {
        mutableStateOf(
            Product(brand = "Apple")
                .apply {
                    images = listOf("")
                })
    }
    ShoppingAppTheme {
        ProductDetailsContent(productDetailsState) {

        }
    }
}