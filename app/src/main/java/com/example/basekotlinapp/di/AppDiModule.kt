package com.example.basekotlinapp.di

import com.example.basekotlinapp.api.DummyData
import com.example.basekotlinapp.api.ModelApi
import com.example.basekotlinapp.api.ModelApiImpl
import com.example.basekotlinapp.repository.ModelRepository
import com.example.basekotlinapp.repository.ModelRepositoryImpl
import com.example.basekotlinapp.viewmodels.DetailViewModel
import com.example.basekotlinapp.viewmodels.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appDiModule = module {
    single<ModelApi> { ModelApiImpl(dummyData = get()) }
    single<ModelRepository> { ModelRepositoryImpl(modelApi = get()) }
    single<DummyData> { DummyData() }
}

val viewModelModule = module {
    viewModel<ListViewModel> { ListViewModel(modelRepository = get()) }

    viewModel<DetailViewModel> { (modelItemId: String) ->
        DetailViewModel(
            modelItemId = modelItemId,
            modelRepository = get()
        )
    }
}