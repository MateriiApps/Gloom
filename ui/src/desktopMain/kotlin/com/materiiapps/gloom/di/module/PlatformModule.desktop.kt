package com.materiiapps.gloom.di.module

import com.materiiapps.gloom.ui.screen.release.viewmodel.ReleaseViewModel
import com.materiiapps.gloom.ui.util.AppIconSetter
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun platformModule() = module {

    factoryOf(::ReleaseViewModel)

    singleOf(::AppIconSetter)

}