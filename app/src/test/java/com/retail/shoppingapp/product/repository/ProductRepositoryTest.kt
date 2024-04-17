package com.retail.shoppingapp.product.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.retail.shoppingapp.network.ApiService
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class ProductRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var productRepository: ProductRepository
    private lateinit var apiService: ApiService

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        apiService = mockk<ApiService>()
        productRepository = ProductRepositoryImpl(apiService)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get Product By Id Success case`() = runTest {
        val product = Product(brand = "LG")
        val successResponse = Response.success(product)
        coEvery { apiService.getProductById(1) } returns successResponse
        productRepository.invoke(1).collect {
            if (it is Resource.Success) {
                Truth.assertThat(it.data).isEqualTo(Product(brand = "LG"))
                Truth.assertThat(it.message).isEqualTo("Success")
            }
        }
    }

}








