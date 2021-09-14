package com.example.basekotlinapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basekotlinapp.databinding.ItemModelBinding
import com.example.basekotlinapp.model.ModelItem

class ItemRecyclerAdapter : RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null
    private var items = emptyList<ModelItem>()

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
        fun bind(modelItem: ModelItem) {
            with(binding) {
                tvCode.text = modelItem.code
                tvName.text = modelItem.name
                tvTitle.text = modelItem.title

                root.setOnClickListener {
                    onItemClickListener?.onItemClick(modelItem.id)
                }
            }
        }
    }

    fun setItems(modelItemList: List<ModelItem>) {
        this.items = modelItemList
        notifyDataSetChanged()
    }

    fun addItem(modelItem: ModelItem) {
        items = items + listOf(modelItem)
        notifyItemInserted(items.size)
    }

    fun removeItem(modelItem: ModelItem) {
        val position = items.indexOf(modelItem)
        (items as MutableList).remove(modelItem)
        notifyItemRemoved(position)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(modelItemId: String?)
    }
}