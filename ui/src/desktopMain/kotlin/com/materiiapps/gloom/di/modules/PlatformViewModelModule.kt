package com.materiiapps.gloom.di.modules

import com.materiiapps.gloom.ui.viewmodels.release.ReleaseViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

actual fun platformViewModelModule() = module {

    factoryOf(::ReleaseViewModel)

}