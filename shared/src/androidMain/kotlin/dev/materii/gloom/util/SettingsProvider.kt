package dev.materii.gloom.util

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class SettingsProvider(
    private val context: Context,
    private val name: String
) {

    actual fun createSettings(): Settings = SharedPreferencesSettings(
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
    )

}