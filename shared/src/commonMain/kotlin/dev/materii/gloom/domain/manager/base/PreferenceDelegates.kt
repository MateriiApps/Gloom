package dev.materii.gloom.domain.manager.base

fun BasePreferenceManager.stringPreference(
    key: String,
    defaultValue: String
) = Preference(
    key = key,
    defaultValue = defaultValue,
    getter = ::getString,
    setter = ::putString
)

fun BasePreferenceManager.booleanPreference(
    key: String,
    defaultValue: Boolean
) = Preference(
    key = key,
    defaultValue = defaultValue,
    getter = ::getBoolean,
    setter = ::putBoolean
)

fun BasePreferenceManager.intPreference(
    key: String,
    defaultValue: Int
) = Preference(
    key = key,
    defaultValue = defaultValue,
    getter = ::getInt,
    setter = ::putInt
)

fun BasePreferenceManager.floatPreference(
    key: String,
    defaultValue: Float
) = Preference(
    key = key,
    defaultValue = defaultValue,
    getter = ::getFloat,
    setter = ::putFloat
)

inline fun <reified E: Enum<E>> BasePreferenceManager.enumPreference(
    key: String,
    defaultValue: E
) = Preference(
    key = key,
    defaultValue = defaultValue,
    getter = ::getEnum,
    setter = ::putEnum
)