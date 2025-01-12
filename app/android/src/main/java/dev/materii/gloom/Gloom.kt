package dev.materii.gloom

import android.app.Application
import dev.materii.gloom.di.httpModule
import dev.materii.gloom.di.module.loggerModule
import dev.materii.gloom.di.module.managerModule
import dev.materii.gloom.di.module.platformModule
import dev.materii.gloom.di.module.settingsModule
import dev.materii.gloom.di.module.viewModelModule
import dev.materii.gloom.di.repositoryModule
import dev.materii.gloom.di.serviceModule
import dev.materii.gloom.util.VersionName
import dev.materii.gloom.ui.viewmodel.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
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