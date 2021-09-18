package com.example.basekotlinapp.domain.usecases

import com.example.basekotlinapp.data.repository.ItemRepository
import com.example.basekotlinapp.model.ItemModel
import com.example.basekotlinapp.utils.ExecutionResult
import kotlinx.coroutines.flow.Flow

class GetItemByIdUseCase(private val itemRepository: ItemRepository) :
    UseCaseWithParams<String, Flow<ExecutionResult<ItemModel>>>() {

    override suspend fun buildUseCase(params: String): Flow<ExecutionResult<ItemModel>> {
        return itemRepository.getItemById(params)
    }
}