package com.example.basekotlinapp.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.example.basekotlinapp.R
import com.example.basekotlinapp.domain.usecases.AddItemUseCase
import com.example.basekotlinapp.domain.usecases.GetItemByIdUseCase
import com.example.basekotlinapp.domain.usecases.UpdateItemUseCase
import com.example.basekotlinapp.model.ItemModel
import com.example.basekotlinapp.utils.ExecutionResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(
    private val modelItemId: String?,
    private val addItemUseCase: AddItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase
) : ViewModel() {

    private val _modelItemId: MutableLiveData<String?> by lazy { MutableLiveData(modelItemId) }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val isLoading = _modelItemId.distinctUntilChanged().switchMap { id ->
        liveData {
            emit(true)
            if (id != null) {
                getItemByIdUseCase.execute(id).collect {
                    when (it) {
                        is ExecutionResult.Success -> setupFields(it.data)
                        is ExecutionResult.Error -> _errorMessage.value = it.error.message
                        is ExecutionResult.Loading -> emit(true)
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
            updateItemUseCase.execute(itemModel).collect {
                if (it is ExecutionResult.Error) {
                    _errorMessage.value = it.error.message
                }
                buttonClickMarker.value = true
            }
        }
    }

    private fun add(itemModel: ItemModel) {
        viewModelScope.launch {
            addItemUseCase.execute(itemModel).collect {
                if (it is ExecutionResult.Error) {
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