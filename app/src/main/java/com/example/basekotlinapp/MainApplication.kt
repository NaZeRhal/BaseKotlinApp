package com.example.basekotlinapp

import android.app.Application
import com.example.basekotlinapp.data.di.dataDiModule
import com.example.basekotlinapp.domain.di.domainDiModule
import com.example.basekotlinapp.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MainApplication)
            modules(listOf(dataDiModule, domainDiModule, viewModelModule))
        }
    }
}