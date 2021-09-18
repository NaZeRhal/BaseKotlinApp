package com.example.basekotlinapp.data.api

import com.example.basekotlinapp.model.ItemModel
import kotlinx.coroutines.delay
import retrofit2.Response

class ModelApiImpl(private val dummyRemoteDb: DummyRemoteDb) : ModelApi {

    override suspend fun fetchItems(): Response<List<ItemModel>> {
        delay(1000)
        return dummyRemoteDb.fetchList()
    }

    override suspend fun fetchItemById(id: String): Response<ItemModel> {
        return dummyRemoteDb.fetchItemById(id)
    }

    override suspend fun addItem(itemModel: ItemModel): Response<String> {
        delay(1000)
        return dummyRemoteDb.addItem(itemModel)
    }

    override suspend fun update(itemId: String, itemModel: ItemModel): Response<ItemModel> {
        delay(1000)
        return dummyRemoteDb.updateItem(itemId, itemModel)
    }

    override suspend fun deleteItemById(itemId: String): Response<ItemModel> {
        return dummyRemoteDb.deleteItem(itemId)
    }
}