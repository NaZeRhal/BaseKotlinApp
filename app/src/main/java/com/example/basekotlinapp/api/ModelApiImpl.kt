package com.example.basekotlinapp.api

import com.example.basekotlinapp.model.ModelItem
import kotlinx.coroutines.delay
import retrofit2.Response

class ModelApiImpl(private val dummyRemoteDb: DummyRemoteDb) : ModelApi {

    override suspend fun fetchItems(): Response<List<ModelItem>> {
        delay(1000)
        return dummyRemoteDb.fetchList()
    }

    override suspend fun fetchItemById(id: String): Response<ModelItem> {
        delay(1000)
        return dummyRemoteDb.fetchItemById(id)
    }

    override suspend fun addItem(item: ModelItem): Response<Any> {
        delay(1000)
        return dummyRemoteDb.addItem(item)
    }

    override suspend fun update(itemId: String, item: ModelItem): Response<ModelItem> {
        delay(1000)
        return dummyRemoteDb.updateItem(itemId, item)
    }

    override suspend fun deleteItem(itemId: String): Response<Any> {
        return Response.success(false)
    }
}