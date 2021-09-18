package com.example.basekotlinapp.presentation.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.example.basekotlinapp.domain.usecases.DeleteItemUseCase
import com.example.basekotlinapp.domain.usecases.GetItemsUseCase
import com.example.basekotlinapp.model.ItemModel
import com.example.basekotlinapp.utils.ExecutionResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListViewModel(
    private val getItemsUseCase: GetItemsUseCase,
    private val deleteItemUseCase: DeleteItemUseCase
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val isLoading = ObservableBoolean(true)

    val items: LiveData<List<ItemModel>> = liveData {
        getItemsUseCase.execute().collect {
            when (it) {
                is ExecutionResult.Loading -> isLoading.set(true)
                is ExecutionResult.Error -> {
                    isLoading.set(false)
                    _errorMessage.value = it.error.message
                }
                is ExecutionResult.Success -> {
                    isLoading.set(false)
                    emit(it.data)
                }
            }
        }
    }


    fun delete(itemModel: ItemModel) {
        viewModelScope.launch {
            deleteItemUseCase.execute(itemModel).collect {
                if (it is ExecutionResult.Error) {
                    _errorMessage.value = it.error.message
                }
            }
        }
    }
}