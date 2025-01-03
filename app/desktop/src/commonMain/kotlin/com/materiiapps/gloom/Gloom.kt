package com.materiiapps.gloom

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.materiiapps.gloom.di.httpModule
import com.materiiapps.gloom.di.module.loggerModule
import com.materiiapps.gloom.di.module.managerModule
import com.materiiapps.gloom.di.module.platformModule
import com.materiiapps.gloom.di.module.settingsModule
import com.materiiapps.gloom.di.module.viewModelModule
import com.materiiapps.gloom.di.repositoryModule
import com.materiiapps.gloom.di.serviceModule
import com.materiiapps.gloom.ui.App
import com.materiiapps.gloom.ui.screen.auth.LandingScreen
import com.materiiapps.gloom.util.LinkHandler
import com.materiiapps.gloom.util.VersionName
import dev.icerock.moko.resources.compose.stringResource
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    application {
        VersionName = BuildConfig.VERSION_NAME

        startKoin {
            modules(
                httpModule(),
                loggerModule(),
                serviceModule(),
                repositoryModule(),
                settingsModule(),
                managerModule(),
                viewModelModule(),
                platformModule()
            )
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = stringResource(Res.strings.app_name)
        ) {
            App(
                startingScreen = LandingScreen(),
                linkHandler = LinkHandler()
            )
        }
    }
}