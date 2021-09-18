package com.example.basekotlinapp.domain.di

import com.example.basekotlinapp.domain.usecases.*
import org.koin.dsl.module

val domainDiModule = module {
    factory { AddItemUseCase(itemRepository = get()) }
    factory { UpdateItemUseCase(itemRepository = get()) }
    factory { DeleteItemUseCase(itemRepository = get()) }
    factory { GetItemsUseCase(itemRepository = get()) }
    factory { GetItemByIdUseCase(itemRepository = get()) }
}