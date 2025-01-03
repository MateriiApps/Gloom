package com.materiiapps.gloom.di.module

import com.materiiapps.gloom.ui.screen.release.viewmodel.ReleaseViewModel
import com.materiiapps.gloom.ui.util.AppIconSetter
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun platformModule() = module {

    factoryOf(::ReleaseViewModel)

    single { AppIconSetter(get(), get(named("prefs"))) }

}