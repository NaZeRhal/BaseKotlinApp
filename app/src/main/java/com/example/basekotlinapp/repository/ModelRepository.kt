package com.example.basekotlinapp.repository

import com.example.basekotlinapp.model.ItemModel
import com.example.basekotlinapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ModelRepository {
    fun getItems(): Flow<Resource<List<ItemModel>>>

    fun getItemById(id: String): Flow<Resource<ItemModel>>

    fun addItem(itemModel: ItemModel): Flow<Resource<Any>>

    fun updateItem(itemModel: ItemModel): Flow<Resource<ItemModel>>

}