package com.materiiapps.gloom

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.materiiapps.gloom.di.httpModule
import com.materiiapps.gloom.di.modules.loggerModule
import com.materiiapps.gloom.di.modules.managerModule
import com.materiiapps.gloom.di.modules.platformViewModelModule
import com.materiiapps.gloom.di.modules.settingsModule
import com.materiiapps.gloom.di.modules.viewModelModule
import com.materiiapps.gloom.di.repositoryModule
import com.materiiapps.gloom.di.serviceModule
import com.materiiapps.gloom.ui.App
import com.materiiapps.gloom.ui.screens.auth.LandingScreen
import com.materiiapps.gloom.utils.LinkHandler
import com.materiiapps.gloom.utils.VersionName
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
                platformViewModelModule()
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