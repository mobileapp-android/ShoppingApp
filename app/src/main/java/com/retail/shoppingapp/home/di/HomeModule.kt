package com.retail.shoppingapp.home.di

import com.retail.shoppingapp.home.repository.GetProductsRepo
import com.retail.shoppingapp.home.repository.GetProductsRepoImpl
import com.retail.shoppingapp.home.usecase.GetProductListUseCase
import com.retail.shoppingapp.home.usecase.GetProductListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun bindProductListRepo(repo: GetProductsRepoImpl): GetProductsRepo

    @Binds
    abstract fun bindGetProductsUseCase(useCase: GetProductListUseCaseImpl): GetProductListUseCase
}