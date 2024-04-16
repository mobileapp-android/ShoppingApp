package com.retail.shoppingapp.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.retail.shoppingapp.storage.tables.Product

@Database(entities = [Product::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getDao(): MyDao
}