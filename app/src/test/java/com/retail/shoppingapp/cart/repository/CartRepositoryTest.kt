package com.retail.shoppingapp.cart.repository


import com.google.common.truth.Truth
import com.retail.shoppingapp.storage.MyDao
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CartRepositoryTest {

    private lateinit var dao: MyDao
    private lateinit var repository: CartRepository

    @Before
    fun setUp() {
        dao = mockk<MyDao>()
        repository = CartRepositoryImpl(dao)
    }

    @Test
    fun getMyCartDetails() = runTest {
        val testData = listOf(
            Product("LG"),
            Product("Google"),
        )
        every { dao.getAllProducts() } returns testData
        repository.getMyCartDetails().collect {
            if (it is Resource.Success) {
                Truth.assertThat(it.data).isEqualTo(testData)
            }
        }
    }

    @Test
    fun deleteCartItem() = runTest {
        val product = Product("Motorola")
        every { dao.delete(product) } returns Unit
        repository.deleteCartItem(product).collect {
            if (it is Resource.Success) {
                Truth.assertThat(it.data).isTrue()
            }
        }
    }
}