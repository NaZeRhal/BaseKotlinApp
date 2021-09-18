package com.example.basekotlinapp.api

import com.example.basekotlinapp.model.ItemModel
import retrofit2.Response

interface ModelApi {

    suspend fun fetchItems(): Response<List<ItemModel>>

    suspend fun fetchItemById(id: String): Response<ItemModel>

    suspend fun addItem(itemModel: ItemModel): Response<String>

    suspend fun update(itemId: String, itemModel: ItemModel): Response<ItemModel>

    suspend fun deleteItem(itemId: String): Response<Any>
}