package com.retail.shoppingapp.product.usecase

import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import kotlinx.coroutines.flow.Flow

fun interface GetProductByIdUseCase {
    operator fun invoke(productId: Int): Flow<Resource<Product>>
}