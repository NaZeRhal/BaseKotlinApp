package com.example.basekotlinapp.data.repository

import com.example.basekotlinapp.data.api.ModelApi
import com.example.basekotlinapp.data.local.ItemDao
import com.example.basekotlinapp.data.local.ItemRoomModel
import com.example.basekotlinapp.model.ItemModel
import com.example.basekotlinapp.utils.ModelMapping
import com.example.basekotlinapp.utils.ExecutionResult
import com.example.basekotlinapp.utils.getResponse
import com.example.basekotlinapp.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ItemRepositoryImpl(private val modelApi: ModelApi, private val itemDao: ItemDao) :
    ItemRepository {

    override fun getItems(): Flow<ExecutionResult<List<ItemModel>>> = networkBoundResource(
        localQuery = { itemDao.findAll().map { ModelMapping.itemRoomToItemModel(it) } },
        remoteRequest = {
            getResponse(
                request = { modelApi.fetchItems() },
                defaultErrorMessage = "Error fetching items"
            )
        },
        saveFetchResult = { response ->
            response.data?.let { data ->
                val items: List<ItemRoomModel> = data.map {
                    ModelMapping.itemModelToItemRoom(it.id, it)
                }
                itemDao.refreshAll(items)
            }
        }
    )

    override fun getItemById(id: String): Flow<ExecutionResult<ItemModel>> = networkBoundResource(
        localQuery = { itemDao.findByRemoteId(id).map { ModelMapping.itemRoomToItemModel(it) } },
        remoteRequest = {
            getResponse(
                request = { modelApi.fetchItemById(id) },
                defaultErrorMessage = "Error fetching item by id"
            )
        },
        saveFetchResult = {
            //TODO fetch local and remote
        }
    )

    override fun addItem(itemModel: ItemModel): Flow<ExecutionResult<ItemModel>> = flow {
        emit(ExecutionResult.Loading())
        val result = getResponse(
            request = { modelApi.addItem(itemModel) },
            defaultErrorMessage = "Error adding item to remote source"
        )
        when (result) {
            is ExecutionResult.Success -> {
                val newItemRoomModel = itemModel.run {
                    ItemRoomModel(
                        id = 0,
                        remoteId = result.data,
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        phone = phone
                    )
                }
                itemDao.add(newItemRoomModel)
                val emittingItem: ItemModel = ModelMapping.itemRoomToItemModel(newItemRoomModel)
                emit(ExecutionResult.Success(emittingItem))
            }
            else -> emit(ExecutionResult.Error(Throwable("Unable to add item to remote data source")))
        }
    }

    override fun updateItem(itemModel: ItemModel): Flow<ExecutionResult<ItemModel>> =
        flow {
            emit(ExecutionResult.Loading())
            val result = getResponse(
                request = { modelApi.update(itemModel.id, itemModel) },
                defaultErrorMessage = "Error updating item"
            )
            when (result) {
                is ExecutionResult.Success -> {
                    val itemRoomModel = ModelMapping.itemModelToItemRoom(itemModel.id, itemModel)
                    itemDao.refreshByRemoteIds(listOf(itemModel.id), listOf(itemRoomModel))
                    emit(ExecutionResult.Success(result.data))
                }
                else -> emit(ExecutionResult.Error(Throwable("Unable to update item in remote data source")))
            }
        }

    override fun deleteItem(itemModel: ItemModel): Flow<ExecutionResult<ItemModel>> = flow {
        emit(ExecutionResult.Loading())
        val result = getResponse(
            request = { modelApi.deleteItemById(itemModel.id) },
            defaultErrorMessage = "Error deleting item"
        )
        when (result) {
            is ExecutionResult.Success -> {
                itemDao.deleteByRemoteIds(listOf(itemModel.id))
                emit(ExecutionResult.Success(result.data))
            }
            else -> emit(ExecutionResult.Error(Throwable("Error deleting contact in remote source")))
        }
    }
}