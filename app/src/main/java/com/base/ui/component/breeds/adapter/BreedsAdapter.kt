package com.base.ui.component.breeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.base.data.dto.breed.BreedItem
import com.base.databinding.BreedItemBinding
import com.base.ui.base.listeners.RecyclerItemListener


class BreedsAdapter : PagingDataAdapter<BreedItem, BreedsViewHolder>(MovieComparator) {

    lateinit var onClickListener: RecyclerItemListener
    fun setOnclickListener(onClickListener: RecyclerItemListener){
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedsViewHolder {
        val itemBinding = BreedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BreedsViewHolder, position: Int) {
        val breed = getItem(position)!!
        holder.bind(breed, onClickListener)
    }

    object MovieComparator: DiffUtil.ItemCallback<BreedItem>() {
        override fun areItemsTheSame(oldItem: BreedItem, newItem: BreedItem): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BreedItem, newItem: BreedItem): Boolean {
            return oldItem == newItem
        }
    }
}

