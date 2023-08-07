package com.materiiapps.gloom.domain.manager

import com.materiiapps.gloom.domain.manager.base.BasePreferenceManager
import com.materiiapps.gloom.utils.SettingsProvider

class PreferenceManager(provider: SettingsProvider) :
    BasePreferenceManager(provider) {

    var theme by enumPreference("theme", Theme.SYSTEM)
    var monet by booleanPreference("monet", false)

}

enum class Theme {
    SYSTEM,
    LIGHT,
    DARK
}