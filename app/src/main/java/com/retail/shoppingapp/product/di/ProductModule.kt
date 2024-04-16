package com.retail.shoppingapp.product.di

import com.retail.shoppingapp.product.repository.CartRepository
import com.retail.shoppingapp.product.repository.CartRepositoryImpl
import com.retail.shoppingapp.product.repository.ProductRepository
import com.retail.shoppingapp.product.repository.ProductRepositoryImpl
import com.retail.shoppingapp.product.usecase.AddToCartUseCase
import com.retail.shoppingapp.product.usecase.AddToCartUseCaseImpl
import com.retail.shoppingapp.product.usecase.GetProductByIdUseCase
import com.retail.shoppingapp.product.usecase.GetProductByIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ProductModule {
    @Binds
    abstract fun bindProductRepository(repo: ProductRepositoryImpl): ProductRepository

    @Binds
    abstract fun bindGetProductByIdUseCase(useCase: GetProductByIdUseCaseImpl): GetProductByIdUseCase

    @Binds
    abstract fun bindAddToCartUseCase(useCase: AddToCartUseCaseImpl): AddToCartUseCase

    @Binds
    abstract fun bindCartRepository(repo: CartRepositoryImpl): CartRepository

}