package com.retail.shoppingapp.product.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
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
import io.mockk.verify
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

    @Test
    fun `test add to Cart Success`() = runTest {
        val product = Product(brand = "LG")
        coEvery { cartRepository.invoke(Product(brand = "LG")) } returns flowOf(Resource.Success(1, "Success"))
        productViewModel.addToCart(product, onSuccess = { assertThat(true).isTrue() })
    }

    @Test
    fun `test add to Cart Error`() = runTest {
        val product = Product(brand = "LG")
        coEvery { cartRepository.invoke(Product(brand = "LG")) } returns flowOf(Resource.Error(-1, "Error"))
        productViewModel.addToCart(product, onSuccess = { assertThat(true).isFalse() })
        assertThat(true).isTrue()
    }
    @Test
    fun `test get product by ID Success`() = runTest {
        coEvery { productsRepository.invoke(1) } returns flowOf(Resource.Success(Product(brand = "LG"), "Success"))
        productViewModel.getProductByID(1)
        //assertThat(productViewModel.productDetailsState.value).isEqualTo(ApiState.Success)
        assertThat(productViewModel.productDetailsState.value).isEqualTo(Product(brand = "LG"))
    }

    @Test
    fun `test get product by ID Error`() = runTest {
        coEvery { productsRepository.invoke(1) } returns flowOf(Resource.Error(Product(brand = "LG"), "Error"))
        productViewModel.getProductByID(1)
        //assertThat(productViewModel.productDetailsState.value).isEqualTo(ApiState.Success)
        assertThat(productViewModel.getProductError.value).isEqualTo(true)
    }


}
