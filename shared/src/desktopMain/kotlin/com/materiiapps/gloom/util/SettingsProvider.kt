package com.materiiapps.gloom.util

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings

actual class SettingsProvider(
    private val name: String
) {

    actual fun createSettings(): Settings = PreferencesSettings.Factory().create(name)

}