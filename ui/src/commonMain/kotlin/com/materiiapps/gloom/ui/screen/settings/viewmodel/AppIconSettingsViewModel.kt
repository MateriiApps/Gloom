package com.materiiapps.gloom.ui.screen.settings.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import com.materiiapps.gloom.ui.util.AppIcon
import com.materiiapps.gloom.ui.util.AppIconSetter

class AppIconsSettingsViewModel(
    val appIconSetter: AppIconSetter
) : ScreenModel {

    fun setIcon(appIcon: AppIcon) = appIconSetter.setIcon(appIcon)

}