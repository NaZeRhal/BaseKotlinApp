package com.example.basekotlinapp.repository

import com.example.basekotlinapp.model.ModelItem
import com.example.basekotlinapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ModelRepository {
    fun getItems(): Flow<Resource<List<ModelItem>>>

    fun getItemById(id: String): Flow<Resource<ModelItem>>

    fun addItem(item: ModelItem): Flow<Resource<Any>>

}