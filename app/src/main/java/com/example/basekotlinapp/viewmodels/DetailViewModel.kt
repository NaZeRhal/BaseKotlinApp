package com.example.basekotlinapp.viewmodels

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.example.basekotlinapp.R
import com.example.basekotlinapp.model.ItemModel
import com.example.basekotlinapp.repository.ModelRepository
import com.example.basekotlinapp.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.random.Random

class DetailViewModel(
    private val modelItemId: String?,
    private val modelRepository: ModelRepository
) : ViewModel() {

    private val _modelItemId: MutableLiveData<String?> by lazy { MutableLiveData(modelItemId) }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val isLoading = _modelItemId.distinctUntilChanged().switchMap { id ->
        liveData {
            emit(true)
            if (id != null) {
                modelRepository.getItemById(id).collect {
                    when (it) {
                        is Resource.Success -> setupFields(it.data)
                        is Resource.Error -> _errorMessage.value = it.error.message
                        is Resource.Loading -> emit(true)
                    }
                    emit(false)
                }
            } else {
                setupFields(null)
                emit(false)
            }
        }
    }

    private val _buttonClickMarker = MutableLiveData<Boolean>(false)
    val buttonClickMarker = _buttonClickMarker

    val firstName = ObservableField<String?>()
    val lastName = ObservableField<String?>()
    val email = ObservableField<String?>()
    val phone = ObservableField<String?>()
    val buttonTextRes = ObservableInt(R.string.detail_button_add_text)

    fun addOrUpdate() {
        val item = ItemModel(
            id = _modelItemId.value ?: "",
            firstName = firstName.get() ?: "",
            lastName = lastName.get() ?: "",
            email = email.get() ?: "",
            phone = phone.get() ?: ""
        )

        if (_modelItemId.value != null) {
            update(item)
        } else {
            add(item)
        }
    }

    private fun update(itemModel: ItemModel) {
        viewModelScope.launch {
            modelRepository.updateItem(itemModel).collect {
                if (it is Resource.Error) {
                    _errorMessage.value = it.error.message
                }
                buttonClickMarker.value = true
            }
        }
    }

    private fun add(itemModel: ItemModel) {
        viewModelScope.launch {
            modelRepository.addItem(itemModel).collect {
                if (it is Resource.Error) {
                    _errorMessage.value = it.error.message
                }
                buttonClickMarker.value = true
            }
        }
    }

    private fun setupFields(itemModel: ItemModel?) {
        if (itemModel == null) {
            firstName.set("")
            lastName.set("")
            email.set("")
            phone.set("")
            buttonTextRes.set(R.string.detail_button_add_text)
        } else {
            firstName.set(itemModel.firstName)
            lastName.set(itemModel.lastName)
            email.set(itemModel.email)
            phone.set(itemModel.phone)
            buttonTextRes.set(R.string.detail_button_edit_text)
        }
    }
}