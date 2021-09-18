package com.example.basekotlinapp.repository

import com.example.basekotlinapp.api.ModelApi
import com.example.basekotlinapp.model.ModelItem
import com.example.basekotlinapp.utils.Resource
import com.example.basekotlinapp.utils.getResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class ModelRepositoryImpl(private val modelApi: ModelApi) : ModelRepository {

    override fun getItems(): Flow<Resource<List<ModelItem>>> = flow {
        emit(Resource.Loading<List<ModelItem>>())
        val result: Resource<List<ModelItem>> = getResponse(
            request = { modelApi.fetchItems() },
            defaultErrorMessage = "Error fetching items"
        )
        when (result) {
            is Resource.Success -> {
                emit(Resource.Success<List<ModelItem>>(result.data))
            }
            is Resource.Error -> {
                emit(Resource.Error<List<ModelItem>>(result.error))
            }
            else -> emit(Resource.Loading<List<ModelItem>>())
        }
    }

    override fun getItemById(id: String): Flow<Resource<ModelItem>> = flow {
        emit(Resource.Loading<ModelItem>())
        val result: Resource<ModelItem> = getResponse(
            request = { modelApi.fetchItemById(id) },
            defaultErrorMessage = "Error fetching item by id"
        )
        when (result) {
            is Resource.Success -> {
                emit(Resource.Success<ModelItem>(result.data))
            }
            is Resource.Error -> {
                emit(Resource.Error<ModelItem>(result.error))
            }
            else -> emit(Resource.Loading<ModelItem>())
        }
    }

    override fun addItem(item: ModelItem): Flow<Resource<Any>> = flow {
        emit(Resource.Loading<Any>())
        val result = getResponse(
            request = { modelApi.addItem(item) },
            defaultErrorMessage = "Error adding item"
        )
        when (result) {
            is Resource.Success<Any> -> emit(Resource.Success(true))
            else -> emit(Resource.Error<Any>(Throwable("Unable to add item to remote data source")))
        }
    }

    override fun updateItem(itemId: String, item: ModelItem): Flow<Resource<ModelItem>> = flow {
        emit(Resource.Loading<ModelItem>())
        val result = getResponse(
            request = { modelApi.update(itemId, item) },
            defaultErrorMessage = "Error updating item"
        )
        when (result) {
            is Resource.Success<ModelItem> -> emit(Resource.Success(result.data))
            else -> emit(Resource.Error<ModelItem>(Throwable("Unable to update item in remote data source")))
        }
    }
}