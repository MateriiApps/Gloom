package com.materiapps.gloom.ui.viewmodels.settings

import cafe.adriel.voyager.core.model.ScreenModel
import com.materiapps.gloom.domain.manager.PreferenceManager

class AppearanceSettingsViewModel(
    val prefs: PreferenceManager
) : ScreenModel