package com.retail.shoppingapp.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.retail.shoppingapp.MainCoroutineRule
import com.retail.shoppingapp.home.repository.GetProductsRepo
import com.retail.shoppingapp.home.usecase.GetProductListUseCaseImpl
import com.retail.shoppingapp.storage.tables.Product
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var getProductsRepo: GetProductsRepo

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getProductsRepo = mockk<GetProductsRepo>()
        val testData = PagingData.from(listOf<Product>())
        coEvery { getProductsRepo.invoke() } returns flowOf(testData)
        homeViewModel = HomeViewModel(GetProductListUseCaseImpl(getProductsRepo))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `test get product list`() = runTest {
        val testProductsList = PagingData.from(
            listOf(
                Product(brand = "LG"),
                Product(brand = "TATA"),
                Product(brand = "BOSCH"),
            )
        )
        coEvery { getProductsRepo.invoke() } returns flowOf(testProductsList)
        homeViewModel.getProductsList()
        homeViewModel.productState.value = testProductsList
        val data = homeViewModel.productState.first()
        TestCase.assertEquals(testProductsList, data)
    }
}

