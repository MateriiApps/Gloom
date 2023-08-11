package com.materiiapps.gloom.domain.manager.base

import com.materiiapps.gloom.utils.SettingsProvider

actual abstract class BasePreferenceManager(
    provider: SettingsProvider
) {

    actual val prefs = provider.createSettings()

    actual fun getString(key: String, defaultValue: String) =
        prefs.getString(key, defaultValue)

    actual fun getBoolean(key: String, defaultValue: Boolean) =
        prefs.getBoolean(key, defaultValue)

    actual fun getInt(key: String, defaultValue: Int) = prefs.getInt(key, defaultValue)
    actual fun getFloat(key: String, defaultValue: Float) =
        prefs.getFloat(key, defaultValue)

    actual inline fun <reified E : Enum<E>> getEnum(key: String, defaultValue: E) =
        enumValueOf<E>(getString(key, defaultValue.name))

    actual fun putString(key: String, value: String) = prefs.putString(key, value)
    actual fun putBoolean(key: String, value: Boolean) = prefs.putBoolean(key, value)
    actual fun putInt(key: String, value: Int) = prefs.putInt(key, value)
    actual fun putFloat(key: String, value: Float) = prefs.putFloat(key, value)

    actual inline fun <reified E : Enum<E>> putEnum(key: String, value: E) =
        putString(key, value.name)

}