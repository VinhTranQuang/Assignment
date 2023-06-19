package com.base.ui.component.breeds.adapter

import androidx.recyclerview.widget.RecyclerView
import com.base.R
import com.base.data.dto.breed.BreedItem
import com.base.databinding.BreedItemBinding
import com.base.ui.base.listeners.RecyclerItemListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BreedsViewHolder(private val itemBinding: BreedItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(recipesItem: BreedItem, recyclerItemListener: RecyclerItemListener) {
        itemBinding.tvName.text = recipesItem.name
        if(recipesItem?.image?.url?.isNotEmpty()!!) {
                Glide
                    .with(itemView)
                    .load(recipesItem.image.url)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(false)
                    .placeholder(R.drawable.ic_healthy_food)
                    .into(itemBinding.ivRecipeItemImage)
        } else if(recipesItem?.url?.isNotEmpty()!!){
            Glide
                .with(itemView)
                .load(recipesItem.url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.ic_healthy_food)
                .into(itemBinding.ivRecipeItemImage)
        }
        itemBinding.btnDownload.setOnClickListener {
            GlobalScope.launch {
                recyclerItemListener.onItemSelected(recipesItem)
            }

        }
    }
}

