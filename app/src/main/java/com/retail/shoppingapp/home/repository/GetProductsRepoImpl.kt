package com.retail.shoppingapp.home.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.retail.shoppingapp.network.ApiService
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.utils.Constants.MAX_PAGE_SIZE
import com.retail.shoppingapp.utils.Constants.STARTING_PAGE_INDEX
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class GetProductsRepoImpl @Inject constructor(
    private val apiService: ApiService,
) : GetProductsRepo {
    override fun invoke(): Flow<PagingData<Product>> = Pager(
        config = PagingConfig(pageSize = MAX_PAGE_SIZE, prefetchDistance = 2),
        pagingSourceFactory = { ProductPagingSource(apiService) }
    ).flow
}

class ProductPagingSource(private val apiService: ApiService) : PagingSource<Int, Product>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val currentPage = params.key ?: STARTING_PAGE_INDEX
            apiService.getProducts(10, currentPage).body().let { resp ->
                LoadResult.Page(
                    data = resp?.products ?: listOf(),
                    prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - MAX_PAGE_SIZE,
                    nextKey = if (resp?.products!!.isEmpty()) null else resp.skip + MAX_PAGE_SIZE
                )
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

}