package com.retail.shoppingapp.storage

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.retail.shoppingapp.storage.tables.Product
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


class MyDatabaseTest {
    private lateinit var myDatabase: MyDatabase
    private lateinit var myDao: MyDao

    @Before
    fun setUp() {
        myDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MyDatabase::class.java
        ).build()
        myDao = myDatabase.getDao()
    }

    @After
    fun tearDown() {
        myDatabase.close()
    }

    @Test
    fun addToCartTest() = runBlocking {
        val product = Product(
            brand = "MI",
            title = "Redmi Note 10"
        )
        myDao.insertProduct(product)
        val allProductsList = myDao.getAllProducts()
        Truth.assertThat(allProductsList).contains(product)
    }

    @Test
    fun getCartTest() = runBlocking {
        val products = listOf(
            Product(brand = "MI", title = "Redmi Note 10"),
            Product(brand = "Google", title = "Pixel 5"),
            Product(brand = "RealMe", title = "C15"),
            Product(brand = "Samsung", title = "Galaxy S24 Ultra"),
        )
        myDao.insertAll(*products.toTypedArray())
        val allProductsList = myDao.getAllProducts()
        Truth.assertThat(allProductsList).contains(Product(brand = "Google", title = "Pixel 5"))
    }

    @Test
    fun deleteCartTest() = runBlocking{
        val products = listOf(
            Product(brand = "MI", title = "Redmi Note 10"),
            Product(brand = "Google", title = "Pixel 5"),
            Product(brand = "RealMe", title = "C15"),
            Product(brand = "Samsung", title = "Galaxy S24 Ultra"),
        )
        myDao.insertAll(*products.toTypedArray())
        val allProductsList = myDao.getAllProducts()
        val productOne = allProductsList[0]
        val productTwo = allProductsList[1]
        myDao.delete(productOne)
        val updatedProductsList = myDao.getAllProducts()
        Truth.assertThat(updatedProductsList).doesNotContain(productOne)
        Truth.assertThat(updatedProductsList).contains(productTwo)
    }
}





