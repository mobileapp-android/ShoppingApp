package com.retail.shoppingapp.product.repository

import com.google.common.truth.Truth
import com.retail.shoppingapp.storage.MyDao
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class CartRepositoryTest {
    private lateinit var cartRepository: CartRepository
    private lateinit var myDao: MyDao

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        myDao = mockk<MyDao>()
        cartRepository = CartRepositoryImpl(myDao)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test add to cart`() = runTest {
        val product = Product(brand = "LG")
        coEvery { myDao.insertProduct(product = product) } returns 1
        cartRepository.invoke(product).collect {
            if (it is Resource.Success) {
                Truth.assertThat(it.data).isEqualTo(1)
                Truth.assertThat(it.message).isEqualTo("Success")
            }
        }
    }
}
