package com.example.basekotlinapp.repository

import com.example.basekotlinapp.api.ModelApi
import com.example.basekotlinapp.local.ItemDao
import com.example.basekotlinapp.local.ItemRoomModel
import com.example.basekotlinapp.model.ItemModel
import com.example.basekotlinapp.utils.ModelMapping
import com.example.basekotlinapp.utils.Resource
import com.example.basekotlinapp.utils.getResponse
import com.example.basekotlinapp.utils.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ModelRepositoryImpl(private val modelApi: ModelApi, private val itemDao: ItemDao) :
    ModelRepository {

    override fun getItems(): Flow<Resource<List<ItemModel>>> = networkBoundResource(
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

    override fun getItemById(id: String): Flow<Resource<ItemModel>> = networkBoundResource(
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

    override fun addItem(itemModel: ItemModel): Flow<Resource<ItemModel>> = flow {
        emit(Resource.Loading())
        val result = getResponse(
            request = { modelApi.addItem(itemModel) },
            defaultErrorMessage = "Error adding item to remote source"
        )
        when (result) {
            is Resource.Success -> {
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
                emit(Resource.Success(emittingItem))
            }
            else -> emit(Resource.Error(Throwable("Unable to add item to remote data source")))
        }
    }

    override fun updateItem(itemModel: ItemModel): Flow<Resource<ItemModel>> =
        flow {
            emit(Resource.Loading())
            val result = getResponse(
                request = { modelApi.update(itemModel.id, itemModel) },
                defaultErrorMessage = "Error updating item"
            )
            when (result) {
                is Resource.Success -> {
                    val itemRoomModel = ModelMapping.itemModelToItemRoom(itemModel.id, itemModel)
                    itemDao.refreshByRemoteIds(listOf(itemModel.id), listOf(itemRoomModel))
                    emit(Resource.Success(result.data))
                }
                else -> emit(Resource.Error(Throwable("Unable to update item in remote data source")))
            }
        }
}