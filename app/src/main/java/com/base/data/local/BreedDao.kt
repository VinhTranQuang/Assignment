package com.base.data.local
import androidx.paging.PagingSource
import androidx.room.*
import com.base.data.dto.breed.BreedItem

@Dao
interface BreedDao {
    @Query("SELECT * FROM breeds")
    fun getBreedsFromLocal() : PagingSource<Int, BreedItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBreedToLocal(item: BreedItem)

    @Query("SELECT * FROM breeds Where id = :id ")
    fun getBreedsFromLocalWithId(id: String) : PagingSource<Int, BreedItem>
}