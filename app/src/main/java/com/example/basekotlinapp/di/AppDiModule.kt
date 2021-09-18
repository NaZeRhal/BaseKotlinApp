package com.example.basekotlinapp.di

import com.example.basekotlinapp.api.DummyRemoteDb
import com.example.basekotlinapp.api.ModelApi
import com.example.basekotlinapp.api.ModelApiImpl
import com.example.basekotlinapp.repository.ModelRepository
import com.example.basekotlinapp.repository.ModelRepositoryImpl
import com.example.basekotlinapp.viewmodels.DetailViewModel
import com.example.basekotlinapp.viewmodels.ListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appDiModule = module {
    single<ModelApi> { ModelApiImpl(dummyRemoteDb = get()) }
    single<ModelRepository> { ModelRepositoryImpl(modelApi = get()) }
    single<DummyRemoteDb> { DummyRemoteDb(androidContext()) }
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