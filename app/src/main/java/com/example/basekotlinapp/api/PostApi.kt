package com.example.basekotlinapp.api

import com.example.basekotlinapp.model.Post
import retrofit2.Response

interface PostApi {

    suspend fun fetchPosts(): Response<List<Post>>
}