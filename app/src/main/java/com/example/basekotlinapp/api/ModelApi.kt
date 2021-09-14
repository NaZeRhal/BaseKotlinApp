package com.example.basekotlinapp.api

import com.example.basekotlinapp.model.ModelItem
import retrofit2.Response

interface ModelApi {

    suspend fun fetchItems(): Response<List<ModelItem>>

    suspend fun fetchItemById(id: String): Response<ModelItem>

    suspend fun addItem(item: ModelItem): Response<Any>

    suspend fun update(itemId: String, item: ModelItem): Response<Any>

    suspend fun deleteItem(itemId: String): Response<Any>
}