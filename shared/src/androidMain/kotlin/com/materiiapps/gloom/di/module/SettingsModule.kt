package com.materiiapps.gloom.di.module

import com.materiiapps.gloom.util.SettingsProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun settingsModule() = module {

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