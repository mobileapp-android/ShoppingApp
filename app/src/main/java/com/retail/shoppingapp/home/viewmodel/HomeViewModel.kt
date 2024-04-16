package com.retail.shoppingapp.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.home.usecase.GetProductListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productListUseCase: GetProductListUseCase
) : ViewModel() {

    private val _productState: MutableStateFlow<PagingData<Product>> =
        MutableStateFlow(value = PagingData.empty())
    val productState: MutableStateFlow<PagingData<Product>> get() = _productState

    init {
        getProductsList()
    }

    /**
     * Get product list download the list of product from the server
     * it update the product list state and update the paginated data
     */
    fun getProductsList() = viewModelScope.launch {
        productListUseCase.invoke()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect { _productState.value = it }
    }
}