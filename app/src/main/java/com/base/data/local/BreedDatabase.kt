package com.base.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.base.data.dto.breed.BreedItem

@Database(
    entities = [BreedItem::class], // Tell the database the entries will hold data of this type
    version = 1,
    exportSchema = false
)

abstract class BreedDatabase : RoomDatabase() {

    abstract fun getYourDao(): BreedDao
}