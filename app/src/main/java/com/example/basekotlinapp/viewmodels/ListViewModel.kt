package com.example.basekotlinapp.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.basekotlinapp.model.ItemModel
import com.example.basekotlinapp.repository.ModelRepository
import com.example.basekotlinapp.utils.Resource
import kotlinx.coroutines.flow.collect

class ListViewModel(private val modelRepository: ModelRepository) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val isLoading = ObservableBoolean(true)

    val items: LiveData<List<ItemModel>> = liveData {
        modelRepository.getItems().collect {
            when (it) {
                is Resource.Loading -> isLoading.set(true)
                is Resource.Error -> {
                    isLoading.set(false)
                    _errorMessage.value = it.error.message
                }
                is Resource.Success -> {
                    isLoading.set(false)
                    emit(it.data)
                }
            }

        }
    }
}