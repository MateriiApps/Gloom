package com.materiiapps.gloom.domain.manager

import com.materiiapps.gloom.domain.manager.base.BasePreferenceManager
import com.materiiapps.gloom.domain.manager.base.booleanPreference
import com.materiiapps.gloom.domain.manager.base.enumPreference
import com.materiiapps.gloom.utils.SettingsProvider
import com.materiiapps.gloom.utils.supportsMonet

class PreferenceManager(provider: SettingsProvider) :
    BasePreferenceManager(provider) {

    var theme by enumPreference("theme", Theme.SYSTEM)
    var monet by booleanPreference("monet", supportsMonet)

}

enum class Theme {
    SYSTEM,
    LIGHT,
    DARK
}