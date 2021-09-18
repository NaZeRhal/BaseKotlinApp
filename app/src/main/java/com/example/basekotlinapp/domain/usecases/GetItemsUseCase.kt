package com.example.basekotlinapp.domain.usecases

import com.example.basekotlinapp.data.repository.ItemRepository
import com.example.basekotlinapp.model.ItemModel
import com.example.basekotlinapp.utils.ExecutionResult
import kotlinx.coroutines.flow.Flow

class GetItemsUseCase(private val itemRepository: ItemRepository) :
    UseCase<Flow<ExecutionResult<List<ItemModel>>>>() {

    override suspend fun buildUseCase(): Flow<ExecutionResult<List<ItemModel>>> {
        return itemRepository.getItems()
    }
}