package dev.materii.gloom

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.di.httpModule
import dev.materii.gloom.di.module.*
import dev.materii.gloom.di.repositoryModule
import dev.materii.gloom.di.serviceModule
import dev.materii.gloom.ui.App
import dev.materii.gloom.ui.screen.auth.LandingScreen
import dev.materii.gloom.util.VersionName
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
                linkHandler = dev.materii.gloom.util.LinkHandler()
            )
        }
    }
}