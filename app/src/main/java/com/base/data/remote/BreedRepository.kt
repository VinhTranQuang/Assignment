package com.base.data.remote

import androidx.paging.PagingData
import com.base.data.dto.breed.BreedItem
import com.base.data.dto.breed.Image
import kotlinx.coroutines.flow.Flow


interface BreedRepository {
    suspend fun requestBreeds(page: Int, limit: Int, apiKey: String): Flow<PagingData<BreedItem>>
    suspend fun requestSearchBreeds(id: String, page: Int, limit: Int, apiKey: String): Flow<BreedItem>
    fun saveBreedToLocal(item: BreedItem)
    suspend fun requestGetImage(id: String, page: Int, limit: Int, apiKey: String): Flow<Image>
}
