package com.materiiapps.gloom.domain.manager.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.russhwolf.settings.Settings
import kotlin.reflect.KProperty

expect abstract class BasePreferenceManager {

    val prefs: Settings

    fun getString(key: String, defaultValue: String): String
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun getInt(key: String, defaultValue: Int): Int
    fun getFloat(key: String, defaultValue: Float): Float
    inline fun <reified E : Enum<E>> getEnum(key: String, defaultValue: E): E

    fun putString(key: String, value: String)
    fun putBoolean(key: String, value: Boolean)
    fun putInt(key: String, value: Int)
    fun putFloat(key: String, value: Float)
    inline fun <reified E : Enum<E>> putEnum(key: String, value: E)

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