package com.retail.shoppingapp.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.utils.Constants.PRODUCT_TABLE

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product): Long

    @Query("SELECT * FROM $PRODUCT_TABLE")
    fun getAllProducts(): List<Product>

    @Insert
    fun insertAll(vararg product: Product)

    @Delete
    fun delete(product: Product)
}