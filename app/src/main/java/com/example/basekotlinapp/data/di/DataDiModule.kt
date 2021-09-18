package com.example.basekotlinapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.basekotlinapp.data.api.DummyRemoteDb
import com.example.basekotlinapp.data.api.ItemRestService
import com.example.basekotlinapp.data.api.ItemRestApi
import com.example.basekotlinapp.data.api.ItemRestApiImpl
import com.example.basekotlinapp.data.local.ItemDao
import com.example.basekotlinapp.data.local.ItemRoomDatabase
import com.example.basekotlinapp.data.repository.ItemRepository
import com.example.basekotlinapp.data.repository.ItemRepositoryImpl
import com.example.basekotlinapp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataDiModule = module {
    single<DummyRemoteDb> { DummyRemoteDb(androidContext()) }
    single<ItemDao> { createItemDao(androidContext()) }

    single<ItemRestApi> { ItemRestApiImpl(dummyRemoteDb = get()) }
    single<ItemRepository> { ItemRepositoryImpl(modelApi = get(), itemDao = get()) }
}

fun createItemDao(context: Context): ItemDao {
    val db = Room.databaseBuilder(
        context,
        ItemRoomDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()
    return db.itemDao()
}

fun createContactService(): ItemRestService {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ItemRestService::class.java)
}