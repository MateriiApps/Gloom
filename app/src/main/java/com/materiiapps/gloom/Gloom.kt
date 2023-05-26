package com.materiiapps.gloom

import android.app.Application
import com.materiiapps.gloom.di.modules.*
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
                managerModule(),
                viewModelModule()
            )
        }
    }

}