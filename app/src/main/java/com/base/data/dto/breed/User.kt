package com.base.data.dto.breed


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "email")
    val email: String = "",
    @Json(name = "latlng")
    val latlng: String = "",
    @Json(name = "name")
    val name: String = ""
)
