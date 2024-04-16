package com.retail.shoppingapp.cart.di

import com.retail.shoppingapp.cart.repository.CartRepository
import com.retail.shoppingapp.cart.repository.CartRepositoryImpl
import com.retail.shoppingapp.cart.usecase.DeleteItemUsecase
import com.retail.shoppingapp.cart.usecase.DeleteItemUsecaseImpl
import com.retail.shoppingapp.cart.usecase.GetCartUsecase
import com.retail.shoppingapp.cart.usecase.GetCartUsecaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class CartModule {
    @Binds
    abstract fun bindCartRepository(cartRepository: CartRepositoryImpl): CartRepository

    @Binds
    abstract fun bindGetFromCartUseCase(useCase: GetCartUsecaseImpl): GetCartUsecase

    @Binds
    abstract fun bindDeleteFormCartUseCase(useCase: DeleteItemUsecaseImpl): DeleteItemUsecase

}