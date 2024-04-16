package com.retail.shoppingapp.home.usecase

import androidx.paging.PagingData
import com.retail.shoppingapp.home.repository.GetProductsRepo
import com.retail.shoppingapp.storage.tables.Product
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class GetProductListUseCaseImpl@Inject constructor(
    private val getProducts: GetProductsRepo
) : GetProductListUseCase {
    override suspend fun invoke(): Flow<PagingData<Product>> = getProducts.invoke()
}