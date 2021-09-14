package com.example.basekotlinapp.adapter

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("toggleVisibility")
fun toggleVisibility(v: View, isVisible: Boolean) {
    Log.i("DBG", "toggleVisibility: $isVisible")
    v.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("bindAdapter")
fun RecyclerView.bindAdapter(itemAdapter: ItemRecyclerAdapter) {
    this.run {
        hasFixedSize()
        layoutManager = LinearLayoutManager(this.context)
        adapter = itemAdapter
    }
}