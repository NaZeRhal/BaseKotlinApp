package com.example.basekotlinapp.utils

import android.util.Log
import kotlinx.coroutines.flow.*
import retrofit2.Response


inline fun <reified ResultType, reified RequestType> networkBoundResource(
    crossinline localQuery: () -> Flow<ResultType>,
    crossinline remoteRequest: suspend () -> Resource<RequestType>,
    crossinline saveFetchResult: suspend (Resource<RequestType>) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
) = flow {
    emit(Resource.Loading())
    val data = localQuery().first()
    if (shouldFetch(data)) {
        emit(Resource.Loading())
        val response = remoteRequest()
        if (response is Resource.Success) {
            saveFetchResult(response)
            emitAll(localQuery().map { Resource.Success(it) })
        } else if (response is Resource.Error) {
            emitAll(localQuery().map { Resource.Error<ResultType>(response.error) })
        }
    } else {
        emitAll(localQuery().map { Resource.Success(it) })
    }
}

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