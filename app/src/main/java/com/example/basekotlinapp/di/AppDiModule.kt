package com.example.basekotlinapp.di

import android.content.Context
import androidx.room.Room
import com.example.basekotlinapp.api.DummyRemoteDb
import com.example.basekotlinapp.api.ModelApi
import com.example.basekotlinapp.api.ModelApiImpl
import com.example.basekotlinapp.local.ItemDao
import com.example.basekotlinapp.local.ItemRoomDatabase
import com.example.basekotlinapp.repository.ModelRepository
import com.example.basekotlinapp.repository.ModelRepositoryImpl
import com.example.basekotlinapp.utils.Constants
import com.example.basekotlinapp.viewmodels.DetailViewModel
import com.example.basekotlinapp.viewmodels.ListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appDiModule = module {
    single<DummyRemoteDb> { DummyRemoteDb(androidContext()) }
    single<ItemDao> { createItemDao(androidContext()) }

    single<ModelApi> { ModelApiImpl(dummyRemoteDb = get()) }
    single<ModelRepository> { ModelRepositoryImpl(modelApi = get(), itemDao = get()) }
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

fun createItemDao(context: Context): ItemDao {
    val db = Room.databaseBuilder(
        context,
        ItemRoomDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()
    return db.itemDao()
}