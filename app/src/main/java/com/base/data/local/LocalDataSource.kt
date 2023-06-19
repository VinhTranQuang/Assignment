package com.base.data.local

import androidx.paging.PagingSource
import com.base.data.dto.breed.BreedItem

interface LocalDataSource {
    fun saveBreedsToLocal(breedsList: BreedItem)
    fun getBreedFromLocal(): PagingSource<Int, BreedItem>
    fun getBreedFromLocalWithId(id:String): PagingSource<Int, BreedItem>
}

