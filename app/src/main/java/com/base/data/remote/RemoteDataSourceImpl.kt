package com.base.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.base.APP_APIKEY
import com.base.data.dto.breed.BreedItem
import com.base.data.dto.breed.Image
import com.base.data.remote.service.BreedsService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImpl@Inject constructor(private val apiService: BreedsService) : PagingSource<Int, BreedItem>(), RemoteDataSource {
    private val apiKey = APP_APIKEY
    private var limit = 10
    private val startIndexAt =0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BreedItem> {
        val pageIndex = params.key ?: startIndexAt
        return try {
            val response = apiService.fetchBreeds(pageIndex, limit, apiKey)
            val nextKey =
                if (response.isEmpty()) {
                    null
                } else {
                    pageIndex + 1
                }
            LoadResult.Page(
                data = response,
                prevKey = if (pageIndex == startIndexAt) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, BreedItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun requestSearchBreeds(
        id: String,
        page: Int,
        limit: Int,
        apiKey: String
    ): BreedItem{
        return apiService.fetchSearchBreeds(id, apiKey)
    }

    override suspend fun requestGetImage(
        id: String,
        page: Int,
        limit: Int,
        apiKey: String
    ): Image {
        return apiService.fetchImage(id, apiKey)
    }
}