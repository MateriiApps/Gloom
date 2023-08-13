package com.materiiapps.gloom.di.modules

import com.materiiapps.gloom.utils.SettingsProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun settingsModule() = module {

    single(named("prefs")) {
        SettingsProvider(get(), "prefs")
    }

    single(named("dialogs")) {
        SettingsProvider(get(), "dialogs")
    }

    single(named("auth")) {
        SettingsProvider(get(), "auth")
    }

}