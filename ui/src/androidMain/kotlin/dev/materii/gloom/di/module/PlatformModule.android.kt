package dev.materii.gloom.di.module

import dev.materii.gloom.ui.screen.release.viewmodel.ReleaseViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

actual fun platformModule() = module {

    factoryOf(::ReleaseViewModel)

}