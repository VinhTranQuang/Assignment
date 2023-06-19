package com.base.data.dto.breed


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "breeds")
data class BreedItem(
        @ColumnInfo(name = "url")
        @Json(name = "url") var url: String ="",

        @ColumnInfo(name = "name")
        @Json(name = "name") var name: String = "",

        @Ignore
        @Json(name = "image")val image: Image = Image(),

        @ColumnInfo(name = "id")
        @PrimaryKey
        @Json(name = "id") var id: String = "",

        @ColumnInfo(name = "reference_image_id")
        @Json(name = "reference_image_id") var referenceId: String = "")
