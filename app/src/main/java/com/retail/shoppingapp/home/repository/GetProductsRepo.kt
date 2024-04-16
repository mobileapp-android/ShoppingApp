package com.retail.shoppingapp.home.repository

import androidx.paging.PagingData
import com.retail.shoppingapp.storage.tables.Product
import kotlinx.coroutines.flow.Flow

fun interface GetProductsRepo {
    operator fun invoke(): Flow<PagingData<Product>>
}