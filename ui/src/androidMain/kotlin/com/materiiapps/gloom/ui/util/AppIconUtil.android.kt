package com.materiiapps.gloom.ui.util

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import com.materiiapps.gloom.domain.manager.base.BasePreferenceManager
import com.materiiapps.gloom.domain.manager.base.enumPreference
import com.materiiapps.gloom.util.SettingsProvider

actual class AppIconSetter(
    private val context: Context,
    settingsProvider: SettingsProvider
): BasePreferenceManager(settingsProvider) {

    actual var currentIcon by enumPreference("app_icon", AppIcon.Main)

    actual fun setIcon(appIcon: AppIcon) {
        val pm = context.packageManager

        pm.setComponentEnabledSetting(
            ComponentName(context, currentIcon.aliasName),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        pm.setComponentEnabledSetting(
            ComponentName(context, appIcon.aliasName),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        currentIcon = appIcon
    }

}