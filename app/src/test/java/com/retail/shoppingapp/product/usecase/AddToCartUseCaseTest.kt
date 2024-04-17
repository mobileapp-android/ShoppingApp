package com.retail.shoppingapp.product.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.retail.shoppingapp.product.repository.CartRepository
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class AddToCartUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var addToCartUseCase: AddToCartUseCase
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        cartRepository = mockk<CartRepository>()
        addToCartUseCase = AddToCartUseCaseImpl(cartRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test add to cart success`() = runTest {
        val product = Product(brand = "Google")
        coEvery { cartRepository.invoke(product) } returns flowOf(Resource.Success(1, "Success"))
        addToCartUseCase.invoke(product).collect {
            Truth.assertThat(it.data).isEqualTo(1)
            Truth.assertThat(it.message).isEqualTo("Success")
        }
    }

    @Test
    fun `test aad to cart error`() = runTest {
        coEvery { cartRepository.invoke(Product()) } returns flowOf(Resource.Error(null, "Error"))
        addToCartUseCase.invoke(Product()).collect {
            Truth.assertThat(it.data).isNull()
            Truth.assertThat(it.message).isEqualTo("Error")
        }
    }
}






