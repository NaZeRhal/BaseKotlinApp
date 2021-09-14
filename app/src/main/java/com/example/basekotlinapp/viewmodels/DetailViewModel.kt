package com.example.basekotlinapp.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.example.basekotlinapp.R
import com.example.basekotlinapp.TAG
import com.example.basekotlinapp.model.ModelItem
import com.example.basekotlinapp.repository.ModelRepository
import com.example.basekotlinapp.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.random.Random

class DetailViewModel(
    private val modelItemId: String?,
    private val modelRepository: ModelRepository
) : ViewModel() {

    private val _modelItemId: MutableLiveData<String?> by lazy {
        Log.i(TAG, "${modelItemId}: ")
        MutableLiveData(modelItemId)
    }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val isLoading = _modelItemId.distinctUntilChanged().switchMap { id ->
        Log.i(TAG, "distinct: $id")
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
                Log.i(TAG, "false: ")
                emit(false)
            }
        }
    }

    private val _buttonClickMarker = MutableLiveData<Boolean>(false)
    val buttonClickMarker = _buttonClickMarker

    val code = ObservableField<String?>()
    val title = ObservableField<String?>()
    val name = ObservableField<String?>()
    val buttonTextRes = ObservableInt(R.string.detail_button_add_text)

    fun addOrUpdate() {
        val item = ModelItem(
            id = _modelItemId.value ?: Random.nextInt(0, 100).toString(),
            code = code.get() ?: "",
            title = title.get() ?: "",
            name = name.get() ?: ""
        )

        if (_modelItemId.value != null) {
            update(item)
        } else {
            add(item)
        }
    }

    private fun update(item: ModelItem) {

    }

    private fun add(item: ModelItem) {
        viewModelScope.launch {
            modelRepository.addItem(item).collect {
                if (it is Resource.Error) {
                    _errorMessage.value = it.error.message
                }
                buttonClickMarker.value = true
            }
        }
    }

    private fun setupFields(modelItem: ModelItem?) {
        if (modelItem == null) {
            Log.i(TAG, "setupFields: ")
            code.set("")
            title.set("")
            name.set("")
            buttonTextRes.set(R.string.detail_button_add_text)
        } else {
            code.set(modelItem.code)
            title.set(modelItem.title)
            name.set(modelItem.name)
            buttonTextRes.set(R.string.detail_button_edit_text)
        }
    }
}