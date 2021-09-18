package com.example.basekotlinapp.data.api

import com.example.basekotlinapp.model.ItemModel
import retrofit2.Response

interface ItemRestApi {

    suspend fun fetchItems(): Response<List<ItemModel>>

    suspend fun fetchItemById(id: String): Response<ItemModel>

    suspend fun addItem(itemModel: ItemModel): Response<String>

    suspend fun update(itemId: String, itemModel: ItemModel): Response<ItemModel>

    suspend fun deleteItemById(itemId: String): Response<ItemModel>
}