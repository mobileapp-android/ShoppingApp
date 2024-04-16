package com.retail.shoppingapp.network

import com.retail.shoppingapp.home.model.GetProductResp
import com.retail.shoppingapp.storage.tables.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(ApiRoutes.GET_PRODUCTS)
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): Response<GetProductResp>

    @GET(ApiRoutes.GET_PRODUCT_BY_ID)
    suspend fun getProductById(
        @Path("product_id") productId: Int
    ): Response<Product>
}