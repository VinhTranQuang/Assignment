package com.base.data.remote.service

import com.base.data.dto.breed.BreedItem
import com.base.data.dto.breed.Image
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface BreedsService {
    @GET("breeds?")
    suspend fun fetchBreeds(@Query("page") page: Int?, @Query("limit") limit: Int?,@Query("api_key") api_key: String?): List<BreedItem>
    @GET("breeds/{breed_id}?")
    suspend fun fetchSearchBreeds(@Path("breed_id") breed_id: String, @Query("api_key") api_key: String?): BreedItem
    @GET("images/{image_id}?")
    suspend fun fetchImage(@Path("image_id") imageId: String, @Query("api_key") api_key: String?): Image
}
