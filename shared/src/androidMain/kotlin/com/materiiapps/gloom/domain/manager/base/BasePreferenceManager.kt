package com.materiiapps.gloom.domain.manager.base

import com.materiiapps.gloom.utils.SettingsProvider

actual abstract class BasePreferenceManager(
    provider: SettingsProvider
) {

    actual val prefs = provider.createSettings()

    protected actual fun getString(key: String, defaultValue: String) = prefs.getString(key, defaultValue)

    protected actual fun getBoolean(key: String, defaultValue: Boolean) = prefs.getBoolean(key, defaultValue)
    protected actual fun getInt(key: String, defaultValue: Int) = prefs.getInt(key, defaultValue)
    protected actual fun getFloat(key: String, defaultValue: Float) = prefs.getFloat(key, defaultValue)

    protected actual inline fun <reified E : Enum<E>> getEnum(key: String, defaultValue: E) =
        enumValueOf<E>(getString(key, defaultValue.name))

    protected actual fun putString(key: String, value: String) = prefs.putString(key, value)
    protected actual fun putBoolean(key: String, value: Boolean) = prefs.putBoolean(key, value)
    protected actual fun putInt(key: String, value: Int) = prefs.putInt(key, value)
    protected actual fun putFloat(key: String, value: Float) = prefs.putFloat(key, value)

    protected actual inline fun <reified E : Enum<E>> putEnum(key: String, value: E) =
        putString(key, value.name)

    protected actual fun stringPreference(
        key: String,
        defaultValue: String
    ) = Preference(
        key = key,
        defaultValue = defaultValue,
        getter = ::getString,
        setter = ::putString
    )

    protected actual fun booleanPreference(
        key: String,
        defaultValue: Boolean
    ) = Preference(
        key = key,
        defaultValue = defaultValue,
        getter = ::getBoolean,
        setter = ::putBoolean
    )

    protected actual fun intPreference(
        key: String,
        defaultValue: Int
    ) = Preference(
        key = key,
        defaultValue = defaultValue,
        getter = ::getInt,
        setter = ::putInt
    )

    protected actual fun floatPreference(
        key: String,
        defaultValue: Float
    ) = Preference(
        key = key,
        defaultValue = defaultValue,
        getter = ::getFloat,
        setter = ::putFloat
    )

    protected actual inline fun <reified E : Enum<E>> enumPreference(
        key: String,
        defaultValue: E
    ) = Preference(
        key = key,
        defaultValue = defaultValue,
        getter = ::getEnum,
        setter = ::putEnum
    )
}