package com.example.basekotlinapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.basekotlinapp.databinding.ItemModelBinding
import com.example.basekotlinapp.model.ItemModel

class ItemRecyclerAdapter : RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null
    private val items = mutableListOf<ItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(private val binding: ItemModelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemModel: ItemModel) {
            with(binding) {
                tvFirstName.text = itemModel.firstName
                tvLastName.text = itemModel.lastName
                tvEmail.text = itemModel.email
                tvPhone.text = itemModel.phone

                root.setOnClickListener {
                    onItemClickListener?.onItemClick(itemModel.id)
                }
            }
        }
    }

    fun setItems(itemModelList: List<ItemModel>) {
        val diffResult = DiffUtil.calculateDiff(DiffHelper(itemModelList, items))
        items.clear()
        items.addAll(itemModelList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getItemAt(position: Int): ItemModel = items[position]

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    private class DiffHelper(
        val newItems: List<ItemModel>,
        val oldItems: List<ItemModel>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].id == newItems[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]


    }

    interface OnItemClickListener {
        fun onItemClick(modelItemId: String?)
    }
}