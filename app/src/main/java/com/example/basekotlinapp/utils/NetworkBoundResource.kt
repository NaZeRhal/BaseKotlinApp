package com.example.basekotlinapp.utils

import android.util.Log
import kotlinx.coroutines.flow.*
import retrofit2.Response


inline fun <reified ResultType, reified RequestType> networkBoundResource(
    crossinline localQuery: () -> Flow<ResultType>,
    crossinline remoteRequest: suspend () -> ExecutionResult<RequestType>,
    crossinline saveFetchResult: suspend (ExecutionResult<RequestType>) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
) = flow {
    emit(ExecutionResult.Loading())
    val data = localQuery().first()
    if (shouldFetch(data)) {
        emit(ExecutionResult.Loading())
        val response = remoteRequest()
        if (response is ExecutionResult.Success) {
            saveFetchResult(response)
            emitAll(localQuery().map { ExecutionResult.Success(it) })
        } else if (response is ExecutionResult.Error) {
            emitAll(localQuery().map { ExecutionResult.Error<ResultType>(response.error) })
        }
    } else {
        emitAll(localQuery().map { ExecutionResult.Success(it) })
    }
}

suspend fun <T> getResponse(
    request: suspend () -> Response<T>,
    defaultErrorMessage: String
): ExecutionResult<T> {
    return try {
        Log.i("DBG", "getResponse: ")
        val result = request.invoke()
        val resultData = result.body()
        when {
            result.isSuccessful && resultData != null -> ExecutionResult.Success(resultData)
            else -> ExecutionResult.Error(Throwable(result.errorBody()?.toString() ?: defaultErrorMessage))
        }
    } catch (e: Throwable) {
        ExecutionResult.Error(e)
    }
}