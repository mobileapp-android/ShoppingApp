package com.retail.shoppingapp.product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.retail.shoppingapp.product.usecase.AddToCartUseCase
import com.retail.shoppingapp.product.usecase.GetProductByIdUseCase
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.widgets.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val addToCartUseCase: AddToCartUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private var _productDetailsState = MutableStateFlow(Product())

    val productDetailsState: StateFlow<Product> = _productDetailsState

    private var _isLoading = MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> = _isLoading

    private var _getProductError = MutableStateFlow(false)

    val getProductError: StateFlow<Boolean> = _getProductError

    fun getProductByID(productId: Int?) = viewModelScope.launch {
        getProductByIdUseCase.invoke(productId!!).collect { response ->
            when (response) {
                is Resource.Loading -> {
                    _isLoading.value = true
                    _getProductError.value = false
                }

                is Resource.Success -> {
                    response.data?.let { _productDetailsState.value = it }
                    _isLoading.value = false
                    _getProductError.value = false
                }

                is Resource.Error -> {
                    _isLoading.value = false
                    _getProductError.value = true
                }

                else -> {_getProductError.value = false}
            }
        }
    }

    fun addToCart(product: Product, onSuccess: () -> Unit) = viewModelScope.launch {
        addToCartUseCase.invoke(product).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    onSuccess.invoke()
                }
                else -> {}
            }
        }
    }

}

