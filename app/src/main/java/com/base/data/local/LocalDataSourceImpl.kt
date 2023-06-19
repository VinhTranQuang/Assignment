package com.base.data.local

import androidx.paging.PagingSource
import com.base.data.dto.breed.BreedItem
import javax.inject.Inject

class LocalDataSourceImpl@Inject
constructor(private val breedDao: BreedDao):
    LocalDataSource {
    override fun saveBreedsToLocal(breed: BreedItem) {
        var item: BreedItem = breed
            item.url = breed.image?.url.toString()
            breedDao.saveBreedToLocal(item)
        }

    override fun getBreedFromLocal(): PagingSource<Int, BreedItem> {
        return breedDao.getBreedsFromLocal()
    }

    override fun getBreedFromLocalWithId(id:String):PagingSource<Int, BreedItem> {
        return breedDao.getBreedsFromLocalWithId(id)
    }

}

