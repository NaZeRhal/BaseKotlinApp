package com.example.basekotlinapp.repository

import com.example.basekotlinapp.api.PostApi
import com.example.basekotlinapp.model.Post
import com.example.basekotlinapp.utils.Resource
import com.example.basekotlinapp.utils.getResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostsRepositoryImpl(private val postApi: PostApi) : PostsRepository {

    override fun getPosts(): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading())
        val response: Resource<List<Post>> = getResponse(
            request = { postApi.fetchPosts() },
            defaultErrorMessage = "Error fetching posts"
        )
        when (response) {
            is Resource.Success -> {
                emit(Resource.Success(response.data))
            }
            is Resource.Error -> {
                emit(Resource.Error(response.error))
            }
            else -> emit(Resource.Loading())
        }
    }
}