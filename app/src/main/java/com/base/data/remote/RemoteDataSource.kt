package com.base.data.remote

import com.base.data.dto.breed.BreedItem
import com.base.data.dto.breed.Image

interface RemoteDataSource {
    suspend fun requestSearchBreeds(id: String, page: Int, limit: Int, apiKey:String): BreedItem
    suspend fun requestGetImage(id: String, page: Int, limit: Int, apiKey:String): Image
}
