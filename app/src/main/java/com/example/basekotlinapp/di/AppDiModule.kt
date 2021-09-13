package com.example.basekotlinapp.di

import com.example.basekotlinapp.api.PostApi
import com.example.basekotlinapp.api.PostApiImpl
import com.example.basekotlinapp.repository.PostsRepository
import com.example.basekotlinapp.repository.PostsRepositoryImpl
import com.example.basekotlinapp.viewmodels.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appDiModule = module {
    single<PostApi> { PostApiImpl() }
    single<PostsRepository> { PostsRepositoryImpl(postApi = get()) }
}

val viewModelModule = module {
    viewModel<ListViewModel> { ListViewModel(postsRepository = get()) }
}