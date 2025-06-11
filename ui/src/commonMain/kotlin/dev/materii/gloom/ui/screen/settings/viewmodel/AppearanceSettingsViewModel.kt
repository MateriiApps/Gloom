package dev.materii.gloom.ui.screen.settings.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import dev.materii.gloom.domain.manager.PreferenceManager

class AppearanceSettingsViewModel(
    val prefs: PreferenceManager
): ScreenModel