package com.materiiapps.gloom.ui.screens.settings.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import com.materiiapps.gloom.domain.manager.PreferenceManager

class AppearanceSettingsViewModel(
    val prefs: PreferenceManager
) : ScreenModel