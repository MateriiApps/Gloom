package com.materiiapps.gloom.ui.screen.settings.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import com.materiiapps.gloom.domain.manager.PreferenceManager
import com.materiiapps.gloom.domain.manager.enums.AppIcon
import com.materiiapps.gloom.ui.util.AppIconUtil

class AppIconsSettingsViewModel(
    val preferenceManager: PreferenceManager
): ScreenModel {

    fun setIcon(appIcon: AppIcon) = AppIconUtil.setIcon(appIcon)

}