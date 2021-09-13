package com.example.basekotlinapp.viewmodels

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.basekotlinapp.model.Post
import com.example.basekotlinapp.repository.PostsRepository
import com.example.basekotlinapp.utils.Resource
import kotlinx.coroutines.flow.collect

class ListViewModel(private val postsRepository: PostsRepository) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val isLoading = ObservableBoolean(true)

    val posts: LiveData<List<Post>> = liveData {
        postsRepository.getPosts().collect {
            when (it) {
                is Resource.Loading -> isLoading.set(true)
                is Resource.Error -> {
                    isLoading.set(false)
                    _errorMessage.value = it.error.message
                }
                is Resource.Success -> {
                    Log.i("DBG", "posts: ${it.data}")
                    isLoading.set(false)
                    emit(it.data)
                }
            }

        }
    }
}