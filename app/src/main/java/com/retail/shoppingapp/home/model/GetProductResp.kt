package com.retail.shoppingapp.home.model

import com.google.gson.annotations.SerializedName
import com.retail.shoppingapp.storage.tables.Product

data class GetProductResp(
    @SerializedName("limit")
    var limit: Int = 0,
    @SerializedName("skip")
    var skip: Int = 0,
    @SerializedName("total")
    var total: Int = 0,
    @SerializedName("products")
    var products: List<Product> = listOf()
)