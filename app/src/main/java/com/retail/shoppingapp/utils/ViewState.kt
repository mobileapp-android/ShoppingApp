package com.retail.shoppingapp.utils

import androidx.compose.runtime.Stable

@Stable
data class ViewState<T>(var value: T)