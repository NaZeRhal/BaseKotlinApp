package com.example.basekotlinapp.utils

sealed class ExecutionResult<out T> {
    abstract val data: T?

    class Success<T>(override val data: T) : ExecutionResult<T>()

    class Loading<T> : ExecutionResult<T>() {
        override val data: T? get() = null
    }

    class Error<T>(val error: Throwable) : ExecutionResult<T>() {
        override val data: T? get() = null
    }
}