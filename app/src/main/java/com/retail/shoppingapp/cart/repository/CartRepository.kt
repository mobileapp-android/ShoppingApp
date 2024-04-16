package com.retail.shoppingapp.cart.repository

import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getMyCartDetails(): Flow<Resource<List<Product>>>
    suspend fun deleteCartItem(product: Product): Flow<Resource<Boolean>>
}