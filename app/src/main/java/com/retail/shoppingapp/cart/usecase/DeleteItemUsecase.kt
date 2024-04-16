package com.retail.shoppingapp.cart.usecase

import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import kotlinx.coroutines.flow.Flow

interface DeleteItemUsecase {
    suspend operator fun invoke(product: Product): Flow<Resource<Boolean>>

}