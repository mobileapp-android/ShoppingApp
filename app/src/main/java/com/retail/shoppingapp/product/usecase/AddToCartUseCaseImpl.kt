package com.retail.shoppingapp.product.usecase

import com.retail.shoppingapp.product.repository.CartRepository
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class AddToCartUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : AddToCartUseCase {
    override fun invoke(product: Product): Flow<Resource<Long>> = cartRepository.invoke(product)
}