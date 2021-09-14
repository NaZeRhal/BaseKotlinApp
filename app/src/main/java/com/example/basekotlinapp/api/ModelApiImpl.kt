package com.example.basekotlinapp.api

import android.util.Log
import com.example.basekotlinapp.model.ModelItem
import kotlinx.coroutines.delay
import retrofit2.Response

class ModelApiImpl(private val dummyData: DummyData) : ModelApi {

    override suspend fun fetchItems(): Response<List<ModelItem>> {
        delay(1000)
        Log.i("DBG", "fetchPosts: ${DummyData().fetchList().body()}")
        return dummyData.fetchList()
    }

    override suspend fun fetchItemById(id: String): Response<ModelItem> {
        delay(1000)
        return dummyData.fetchItemById(id)
    }

    override suspend fun addItem(item: ModelItem): Response<Any> {
        delay(1000)
        return dummyData.addItem(item)
    }

    override suspend fun update(itemId: String, item: ModelItem): Response<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(itemId: String): Response<Any> {
        TODO("Not yet implemented")
    }
}