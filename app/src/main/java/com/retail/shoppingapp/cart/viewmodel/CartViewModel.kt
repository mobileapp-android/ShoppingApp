package com.retail.shoppingapp.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.retail.shoppingapp.cart.usecase.DeleteItemUsecase
import com.retail.shoppingapp.cart.usecase.GetCartUsecase
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val deleteItemUsecase: DeleteItemUsecase,
    private val getCartUsecase: GetCartUsecase
) : ViewModel() {

    private var _getCartState = MutableStateFlow(listOf<Product>())

    val getCartState: StateFlow<List<Product>> = _getCartState


    private var _isEmpty = MutableStateFlow(false)

    val isEmpty: StateFlow<Boolean> = _isEmpty

    init {
        getCart()
    }

    private fun getCart() = viewModelScope.launch(Dispatchers.IO) {
        getCartUsecase.invoke().collect {
            when (it) {
                is Resource.Success -> {
                    _getCartState.value = it.data ?: listOf()
                    _isEmpty.value = false
                }

                is Resource.Error -> {
                    _getCartState.value = listOf()
                    _isEmpty.value = true
                }

                else -> {}
            }
        }
    }

    fun deleteItem(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        deleteItemUsecase.invoke(product).collect {
            when (it) {
                is Resource.Success -> {
                    getCart()
                }

                else -> {}
            }
        }
    }
}