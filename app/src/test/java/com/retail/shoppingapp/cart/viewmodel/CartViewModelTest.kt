package com.retail.shoppingapp.cart.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.retail.shoppingapp.MainCoroutineRule
import com.retail.shoppingapp.cart.usecase.DeleteItemUsecase
import com.retail.shoppingapp.cart.usecase.GetCartUsecase
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@Suppress("DEPRECATION")
@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)


    private lateinit var deleteItemUsecase: DeleteItemUsecase
    private lateinit var getCartUsecase: GetCartUsecase
    private lateinit var viewModel: CartViewModel
    val product = Product("Apple")
    val newItems = listOf(Product("New Item"))
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        deleteItemUsecase = mockk<DeleteItemUsecase>()
        getCartUsecase = mockk<GetCartUsecase>()

        coEvery { getCartUsecase.invoke() } returns flowOf(Resource.Success(newItems, "Success"))

        viewModel = CartViewModel(deleteItemUsecase, getCartUsecase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }


    @Test
    fun `test deleteItem`() = testScope.runBlockingTest {
        coEvery { deleteItemUsecase.invoke(product) } returns flowOf(
            Resource.Success(
                true,
                "Success"
            )
        )
        viewModel.deleteItem(product)
        Truth.assertThat(viewModel.getCartState.value).isEqualTo(newItems)
    }

    @Test
    fun `test getCart`() = testScope.runBlockingTest {
        val items = listOf(Product("Samsung"))
        coEvery { getCartUsecase.invoke() } returns flowOf(Resource.Success(items, "Success"))
        viewModel.getCart()
        Truth.assertThat(viewModel.getCartState.value).isEqualTo(items)

    }
}