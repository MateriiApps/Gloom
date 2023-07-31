package com.materiiapps.gloom

import android.app.Application
import com.materiiapps.gloom.di.httpModule
import com.materiiapps.gloom.di.modules.loggerModule
import com.materiiapps.gloom.di.modules.managerModule
import com.materiiapps.gloom.di.modules.settingsModule
import com.materiiapps.gloom.di.modules.viewModelModule
import com.materiiapps.gloom.di.repositoryModule
import com.materiiapps.gloom.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Gloom : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Gloom)
            modules(
                httpModule(),
                loggerModule(),
                serviceModule(),
                repositoryModule(),
                settingsModule(),
                managerModule(),
                viewModelModule()
            )
        }
    }

}