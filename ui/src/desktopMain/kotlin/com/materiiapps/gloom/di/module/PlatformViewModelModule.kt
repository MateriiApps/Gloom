package com.materiiapps.gloom.di.module

import com.materiiapps.gloom.ui.screen.release.viewmodel.ReleaseViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

actual fun platformViewModelModule() = module {

    factoryOf(::ReleaseViewModel)

}