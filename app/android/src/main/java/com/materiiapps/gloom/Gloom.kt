package com.materiiapps.gloom

import android.app.Application
import com.materiiapps.gloom.di.httpModule
import com.materiiapps.gloom.di.modules.loggerModule
import com.materiiapps.gloom.di.modules.managerModule
import com.materiiapps.gloom.di.modules.platformViewModelModule
import com.materiiapps.gloom.di.modules.settingsModule
import com.materiiapps.gloom.di.modules.viewModelModule
import com.materiiapps.gloom.di.repositoryModule
import com.materiiapps.gloom.di.serviceModule
import com.materiiapps.gloom.ui.viewmodels.main.MainViewModel
import com.materiiapps.gloom.utils.VersionName
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module

class Gloom : Application() {

    override fun onCreate() {
        super.onCreate()
        VersionName = BuildConfig.VERSION_NAME

        startKoin {
            androidContext(this@Gloom)
            modules(
                httpModule(),
                loggerModule(),
                serviceModule(),
                repositoryModule(),
                settingsModule(),
                managerModule(),
                viewModelModule(),
                platformViewModelModule(),
                module { viewModelOf(::MainViewModel) } // Cant group with the rest
            )
        }
    }

}