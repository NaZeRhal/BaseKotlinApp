package com.example.basekotlinapp.repository

import com.example.basekotlinapp.model.Post
import com.example.basekotlinapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun getPosts(): Flow<Resource<List<Post>>>
}