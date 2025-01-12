package dev.materii.gloom.util

import com.russhwolf.settings.Settings

expect class SettingsProvider {

    fun createSettings(): Settings

}