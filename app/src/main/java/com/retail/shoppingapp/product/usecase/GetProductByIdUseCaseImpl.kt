package com.retail.shoppingapp.product.usecase

import com.retail.shoppingapp.product.repository.ProductRepository
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class GetProductByIdUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : GetProductByIdUseCase {
    override fun invoke(productId: Int): Flow<Resource<Product>> = productRepository.invoke(productId)
}