package com.example.basekotlinapp.api

import android.util.Log
import com.example.basekotlinapp.data.DummyData
import com.example.basekotlinapp.model.Post
import kotlinx.coroutines.delay
import retrofit2.Response

class PostApiImpl : PostApi {

    override suspend fun fetchPosts(): Response<List<Post>> {
        delay(1500)
        Log.i("DBG", "fetchPosts: ${DummyData.fetchList().body()}")
        return DummyData.fetchList()
    }
}