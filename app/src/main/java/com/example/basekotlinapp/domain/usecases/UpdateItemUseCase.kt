package com.example.basekotlinapp.domain.usecases

import com.example.basekotlinapp.data.repository.ItemRepository
import com.example.basekotlinapp.model.ItemModel
import com.example.basekotlinapp.utils.ExecutionResult
import kotlinx.coroutines.flow.Flow

class UpdateItemUseCase(private val itemRepository: ItemRepository) :
    UseCaseWithParams<ItemModel, Flow<ExecutionResult<ItemModel>>>() {

    override suspend fun buildUseCase(params: ItemModel): Flow<ExecutionResult<ItemModel>> {
        return itemRepository.updateItem(params)
    }
}