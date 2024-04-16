package com.retail.shoppingapp.product.repository

import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import kotlinx.coroutines.flow.Flow

fun interface ProductRepository {
    operator fun invoke(productId: Int): Flow<Resource<Product>>
}