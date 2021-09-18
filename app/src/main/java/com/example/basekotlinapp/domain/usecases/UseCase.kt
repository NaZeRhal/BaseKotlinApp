package com.example.basekotlinapp.domain.usecases

 abstract class UseCase<R> {

     protected abstract suspend fun buildUseCase() : R

     suspend fun execute(): R = buildUseCase()
}