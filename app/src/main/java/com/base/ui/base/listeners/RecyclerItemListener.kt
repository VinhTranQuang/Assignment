package com.base.ui.base.listeners

import com.base.data.dto.breed.BreedItem

interface RecyclerItemListener {
    suspend fun onItemSelected(recipe : BreedItem)
}
