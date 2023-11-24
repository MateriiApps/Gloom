package com.materiiapps.gloom.utils

import com.russhwolf.settings.Settings

expect class SettingsProvider {

    fun createSettings(): Settings

}