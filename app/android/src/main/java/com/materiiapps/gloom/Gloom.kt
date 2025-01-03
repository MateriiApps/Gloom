package com.materiiapps.gloom

import android.app.Application
import com.materiiapps.gloom.di.httpModule
import com.materiiapps.gloom.di.module.loggerModule
import com.materiiapps.gloom.di.module.managerModule
import com.materiiapps.gloom.di.module.platformModule
import com.materiiapps.gloom.di.module.settingsModule
import com.materiiapps.gloom.di.module.viewModelModule
import com.materiiapps.gloom.di.repositoryModule
import com.materiiapps.gloom.di.serviceModule
import com.materiiapps.gloom.ui.viewmodel.main.MainViewModel
import com.materiiapps.gloom.util.VersionName
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
                platformModule(),
                module { viewModelOf(::MainViewModel) } // Cant group with the rest
            )
        }
    }

}