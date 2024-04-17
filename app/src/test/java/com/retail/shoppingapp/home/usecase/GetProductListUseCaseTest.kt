package com.retail.shoppingapp.home.usecase

import androidx.paging.PagingData
import com.retail.shoppingapp.home.repository.GetProductsRepo
import com.retail.shoppingapp.storage.tables.Product
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetProductListUseCaseTest {
    private lateinit var getProductListUseCase: GetProductListUseCase
    private lateinit var getProductsRepo: GetProductsRepo

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        getProductsRepo = mockk<GetProductsRepo>()
        getProductListUseCase = GetProductListUseCaseImpl(getProductsRepo)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test get products api`() = runTest {
        val testProducts = PagingData.from(
            listOf(
                Product(brand = "Google"),
                Product(brand = "MI"),
            )
        )
        coEvery { getProductsRepo.invoke() } returns flowOf(testProducts)
        val emittedData = getProductsRepo.invoke().first()
        TestCase.assertEquals(testProducts, emittedData)
    }
}









