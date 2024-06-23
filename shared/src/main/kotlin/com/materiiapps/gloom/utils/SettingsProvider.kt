package com.materiiapps.gloom.utils

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

class SettingsProvider(
    private val context: Context,
    private val name: String
) {

    fun createSettings(): Settings = SharedPreferencesSettings(
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
    )

}