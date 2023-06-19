package com.base.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.base.data.dto.breed.BreedItem
import com.base.data.dto.breed.Image
import com.base.data.local.LocalDataSource
import com.base.data.remote.service.BreedsService
import com.base.utils.NetworkConnectivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class BreedRepositoryImpl @Inject constructor(
    private val retrofitService: BreedsService,
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineContext,
    private val networkConnectivity: NetworkConnectivity
) :
    BreedRepository {
    val NETWORK_PAGE_SIZE = 10

    override suspend fun requestBreeds(
        page: Int,
        limit: Int,
        apiKey: String
    ): Flow<PagingData<BreedItem>> {
        if (networkConnectivity.isConnected()) {
            return Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { RemoteDataSourceImpl(retrofitService)
                }
            ).flow
        } else {
            return Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { localDataSource.getBreedFromLocal() }
            ).flow
        }
    }

    override suspend fun requestSearchBreeds(
        id: String,
        page: Int,
        limit: Int,
        apiKey: String
    ): Flow<BreedItem> {
        return flow {
            emit(
                RemoteDataSourceImpl(retrofitService).requestSearchBreeds(
                    id,
                    page,
                    limit,
                    apiKey
                )
            )
        }.flowOn(ioDispatcher)
    }

    override fun saveBreedToLocal(item: BreedItem) {
        localDataSource.saveBreedsToLocal(item)
    }

    override suspend fun requestGetImage(
        id: String,
        page: Int,
        limit: Int,
        apiKey: String
    ): Flow<Image> {
        return flow {
            emit(RemoteDataSourceImpl(retrofitService).requestGetImage(id, page, limit, apiKey))
        }.flowOn(ioDispatcher)
    }
}
