package com.example.basekotlinapp.presentation.di

import com.example.basekotlinapp.presentation.viewmodels.DetailViewModel
import com.example.basekotlinapp.presentation.viewmodels.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<ListViewModel> { ListViewModel(getItemsUseCase = get(), deleteItemUseCase = get()) }

    viewModel<DetailViewModel> { (modelItemId: String) ->
        DetailViewModel(
            modelItemId = modelItemId,
            addItemUseCase = get(),
            updateItemUseCase = get(),
            getItemByIdUseCase = get()
        )
    }
}