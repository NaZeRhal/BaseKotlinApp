package com.example.basekotlinapp.utils

import android.util.Log
import retrofit2.Response

suspend fun <T> getResponse(
    request: suspend () -> Response<T>,
    defaultErrorMessage: String
): Resource<T> {
    return try {
        Log.i("DBG", "getResponse: ")
        val result = request.invoke()
        val resultData = result.body()
        when {
            result.isSuccessful && resultData != null -> Resource.Success(resultData)
            else -> Resource.Error(Throwable(result.errorBody()?.toString() ?: defaultErrorMessage))
        }
    } catch (e: Throwable) {
        Resource.Error(e)
    }
}