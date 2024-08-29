package com.materiiapps.gloom.util

import com.russhwolf.settings.Settings

expect class SettingsProvider {

    fun createSettings(): Settings

}