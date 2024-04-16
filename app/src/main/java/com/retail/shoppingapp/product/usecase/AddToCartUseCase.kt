package com.retail.shoppingapp.product.usecase


import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import kotlinx.coroutines.flow.Flow

fun interface AddToCartUseCase {
    operator fun invoke(product: Product): Flow<Resource<Long>>
}