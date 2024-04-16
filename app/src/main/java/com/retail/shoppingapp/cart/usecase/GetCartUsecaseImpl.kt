package com.retail.shoppingapp.cart.usecase

import com.retail.shoppingapp.cart.repository.CartRepository
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class GetCartUsecaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : GetCartUsecase{
    override suspend fun invoke(): Flow<Resource<List<Product>>> = cartRepository.getMyCartDetails()
}