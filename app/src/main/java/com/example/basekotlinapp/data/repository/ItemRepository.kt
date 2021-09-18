package com.example.basekotlinapp.data.repository

import com.example.basekotlinapp.model.ItemModel
import com.example.basekotlinapp.utils.ExecutionResult
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getItems(): Flow<ExecutionResult<List<ItemModel>>>

    fun getItemById(id: String): Flow<ExecutionResult<ItemModel>>

    fun addItem(itemModel: ItemModel): Flow<ExecutionResult<ItemModel>>

    fun updateItem(itemModel: ItemModel): Flow<ExecutionResult<ItemModel>>

    fun deleteItem(itemModel: ItemModel): Flow<ExecutionResult<ItemModel>>

}