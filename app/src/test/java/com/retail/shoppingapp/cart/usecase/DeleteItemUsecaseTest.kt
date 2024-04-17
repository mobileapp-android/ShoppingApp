package com.retail.shoppingapp.cart.usecase

import com.google.common.truth.Truth
import com.retail.shoppingapp.cart.repository.CartRepository
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class DeleteItemUsecaseTest {
    private lateinit var cartRepository: CartRepository
    private lateinit var deleteItemUsecase: DeleteItemUsecase

    @Before
    fun setUp() {
        cartRepository = mockk<CartRepository>()
        deleteItemUsecase = DeleteItemUsecaseImpl(cartRepository)
    }

    @Test
    fun `test delete item success`() = runTest {
        val product = Product("Apple")
        coEvery { cartRepository.deleteCartItem(product) } returns flowOf(Resource.Success(true,"Success"))
        deleteItemUsecase.invoke(product = product).collect {
            if(it is Resource.Success){
                Truth.assertThat(it.data).isTrue()
                Truth.assertThat(it.message).isEqualTo("Success")
            }
        }

    }

    @Test
    fun `test delete item failure`() = runTest {
        val product = Product()
        coEvery { cartRepository.deleteCartItem(product) } returns flowOf(Resource.Error(false,"No item found"))
        deleteItemUsecase.invoke(product = product).collect {
            if(it is Resource.Error){
                Truth.assertThat(it.data).isFalse()
                Truth.assertThat(it.message).isEqualTo("No item found")
            }
        }
    }
}