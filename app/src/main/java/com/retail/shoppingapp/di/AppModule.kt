package com.retail.shoppingapp.di

import android.content.Context
import androidx.room.Room
import com.retail.shoppingapp.network.ApiService
import com.retail.shoppingapp.storage.MyDao
import com.retail.shoppingapp.storage.MyDatabase
import com.retail.shoppingapp.utils.Constants.BASE_URL
import com.retail.shoppingapp.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClientForRetrofit(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): MyDao =
        Room.databaseBuilder(app, MyDatabase::class.java, DATABASE_NAME)
            .build()
            .getDao()

}