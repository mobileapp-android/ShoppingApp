package com.retail.shoppingapp.home.usecase

import androidx.paging.PagingData
import com.retail.shoppingapp.storage.tables.Product
import kotlinx.coroutines.flow.Flow

interface GetProductListUseCase {
    suspend operator fun invoke(): Flow<PagingData<Product>>
}