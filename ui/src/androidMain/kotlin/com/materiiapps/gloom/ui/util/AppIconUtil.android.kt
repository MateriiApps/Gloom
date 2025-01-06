package com.materiiapps.gloom.ui.util

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import com.materiiapps.gloom.domain.manager.PreferenceManager
import com.materiiapps.gloom.domain.manager.enums.AppIcon
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual object AppIconUtil: KoinComponent {

    private val context: Context by inject()
    private val prefs: PreferenceManager by inject()

    actual fun setIcon(appIcon: AppIcon) {
        val pm = context.packageManager

        pm.setComponentEnabledSetting(
            ComponentName(context, prefs.appIcon.aliasName),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        pm.setComponentEnabledSetting(
            ComponentName(context, appIcon.aliasName),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        prefs.appIcon = appIcon
    }

}