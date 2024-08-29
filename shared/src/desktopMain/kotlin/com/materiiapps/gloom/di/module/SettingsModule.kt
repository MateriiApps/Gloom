package com.materiiapps.gloom.di.module

import com.materiiapps.gloom.util.SettingsProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun settingsModule() = module {

    single(named("prefs")) {
        SettingsProvider("prefs")
    }

    single(named("dialogs")) {
        SettingsProvider("dialogs")
    }

    single(named("auth")) {
        SettingsProvider("auth")
    }

}