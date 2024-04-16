package com.retail.shoppingapp.cart.repository

import com.retail.shoppingapp.storage.MyDao
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class CartRepositoryImpl @Inject constructor(
    private val myDao: MyDao
) : CartRepository {
    override suspend fun getMyCartDetails(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        val productItems = myDao.getAllProducts()
        if (productItems.isNotEmpty()) emit(Resource.Success(productItems, message = "Got products !!"))
        else emit(Resource.Error(message = "No data found"))
    }

    override suspend fun deleteCartItem(product: Product): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        myDao.delete(product)
        emit(Resource.Success(true, message = "Deleted.."))
    }

}