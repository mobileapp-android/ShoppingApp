package com.retail.shoppingapp.product.repository

import com.retail.shoppingapp.storage.MyDao
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class CartRepositoryImpl @Inject constructor(private val dao: MyDao) : CartRepository {
    override fun invoke(product: Product): Flow<Resource<Long>> = flow {
        val id = dao.insertProduct(product)
        if (id > 0) emit(Resource.Success(data = id, message = "Success"))
        else emit(Resource.Error(message = "Database error"))
    }
}