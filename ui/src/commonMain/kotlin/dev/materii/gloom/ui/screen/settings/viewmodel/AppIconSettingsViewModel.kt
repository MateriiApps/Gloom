package dev.materii.gloom.ui.screen.settings.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import dev.materii.gloom.domain.manager.PreferenceManager
import dev.materii.gloom.domain.manager.enums.AppIcon
import dev.materii.gloom.ui.util.AppIconUtil

class AppIconsSettingsViewModel(
    val preferenceManager: PreferenceManager
): ScreenModel {

    fun setIcon(appIcon: AppIcon) = AppIconUtil.setIcon(appIcon)

}