package com.retail.shoppingapp.cart.usecase

import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import kotlinx.coroutines.flow.Flow

interface GetCartUsecase {
    suspend operator fun invoke(): Flow<Resource<List<Product>>>

}