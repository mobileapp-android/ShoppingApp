package com.retail.shoppingapp.product.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.retail.shoppingapp.product.repository.CartRepository
import com.retail.shoppingapp.product.repository.ProductRepository
import com.retail.shoppingapp.product.usecase.AddToCartUseCaseImpl
import com.retail.shoppingapp.product.usecase.GetProductByIdUseCaseImpl
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.utils.ApiState
import com.retail.shoppingapp.widgets.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var productsRepository: ProductRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var productViewModel: ProductViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        productsRepository = mockk<ProductRepository>()
        cartRepository = mockk<CartRepository>()
        productViewModel = ProductViewModel(
            addToCartUseCase = AddToCartUseCaseImpl(cartRepository),
            getProductByIdUseCase = GetProductByIdUseCaseImpl(productsRepository)
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

//    @Test
//    fun `test get product by Id`() = runTest {
//        val product = Product(brand = "Samsung")
//        coEvery { productsRepository.invoke(1) } returns flowOf(
//            Resource.Success(product, "Success")
//        )
//        productViewModel.getProductByID(1)
//        assertThat(productViewModel.productDetailsState.value).isEqualTo(ApiState.Success)
//        assertThat(productViewModel.productDetailsState.value.brand).isEqualTo(Product(brand = "Samsung"))
//    }
//
//    @Test
//    fun `test Add To Cart`() = runTest {
//        coEvery { cartRepository.invoke(Product(brand = "Samsung")) } returns flowOf(Resource.Success(1, "Success"))
//        productViewModel.addToCart(Product(brand = "Samsung"), onSuccess = {})
//        assertThat(productViewModel.productDetailsState.value.value.id).isEqualTo(1)
//    }
}
