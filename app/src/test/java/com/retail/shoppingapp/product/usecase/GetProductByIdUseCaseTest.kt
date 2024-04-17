package com.retail.shoppingapp.product.usecase

import com.google.common.truth.Truth
import com.retail.shoppingapp.product.repository.ProductRepository
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
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetProductByIdUseCaseTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var getProductByIdUseCase: GetProductByIdUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        productRepository = mockk<ProductRepository>()
        getProductByIdUseCase = GetProductByIdUseCaseImpl(productRepository = productRepository)
        coEvery { productRepository.invoke(1) } returns  flowOf(Resource.Success(Product(brand = "Google"), "Success"))
        coEvery {  productRepository.invoke(-1) } returns  flowOf(Resource.Error(null, "Error"))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test Get Product By Id Success`() = runTest {
        getProductByIdUseCase.invoke(1).collect {
            Truth.assertThat(it.data).isEqualTo(Product(brand = "Google"))
            Truth.assertThat(it.message).isEqualTo("Success")
        }
    }

    @Test
    fun `test Get Product By Id Error`() = runTest {
        getProductByIdUseCase.invoke(-1).collect {
            Truth.assertThat(it.data).isNull()
            Truth.assertThat(it.message).isEqualTo("Error")
        }
    }
}
