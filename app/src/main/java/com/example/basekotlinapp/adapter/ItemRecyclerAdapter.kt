package com.example.basekotlinapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basekotlinapp.databinding.ItemModelBinding
import com.example.basekotlinapp.model.ItemModel

class ItemRecyclerAdapter : RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null
    private var items = emptyList<ItemModel>()

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
        this.items = itemModelList
        notifyDataSetChanged()
    }

    fun addItem(itemModel: ItemModel) {
        items = items + listOf(itemModel)
        notifyItemInserted(items.size)
    }

    fun removeItem(itemModel: ItemModel) {
        val position = items.indexOf(itemModel)
        (items as MutableList).remove(itemModel)
        notifyItemRemoved(position)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(modelItemId: String?)
    }
}