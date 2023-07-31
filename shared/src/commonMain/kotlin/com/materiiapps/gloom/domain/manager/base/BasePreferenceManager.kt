package com.materiiapps.gloom.domain.manager.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.russhwolf.settings.Settings
import kotlin.reflect.KProperty

expect abstract class BasePreferenceManager {

    val prefs: Settings

    protected fun getString(key: String, defaultValue: String): String
    protected fun getBoolean(key: String, defaultValue: Boolean): Boolean
    protected fun getInt(key: String, defaultValue: Int): Int
    protected fun getFloat(key: String, defaultValue: Float): Float
    protected inline fun <reified E : Enum<E>> getEnum(key: String, defaultValue: E): E

    protected fun putString(key: String, value: String)
    protected fun putBoolean(key: String, value: Boolean)
    protected fun putInt(key: String, value: Int)
    protected fun putFloat(key: String, value: Float)
    protected inline fun <reified E : Enum<E>> putEnum(key: String, value: E)

    protected fun stringPreference(
        key: String,
        defaultValue: String = ""
    ): Preference<String>

    protected fun booleanPreference(
        key: String,
        defaultValue: Boolean
    ): Preference<Boolean>

    protected fun intPreference(
        key: String,
        defaultValue: Int
    ): Preference<Int>

    protected fun floatPreference(
        key: String,
        defaultValue: Float
    ): Preference<Float>

    protected inline fun <reified E : Enum<E>> enumPreference(
        key: String,
        defaultValue: E
    ): Preference<E>
}

class Preference<T>(
    private val key: String,
    defaultValue: T,
    getter: (key: String, defaultValue: T) -> T,
    private val setter: (key: String, newValue: T) -> Unit
) {
    var value by mutableStateOf(getter(key, defaultValue))
        private set

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = value
    operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: T) {
        value = newValue
        setter(key, newValue)
    }
}