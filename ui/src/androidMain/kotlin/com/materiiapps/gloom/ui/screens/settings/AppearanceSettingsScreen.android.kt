package com.materiiapps.gloom.ui.screens.settings

import android.os.Build
import androidx.compose.runtime.Composable
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.domain.manager.PreferenceManager
import com.materiiapps.gloom.ui.components.settings.SettingsSwitch
import dev.icerock.moko.resources.compose.stringResource

@Composable
actual fun DynamicColorSetting(prefs: PreferenceManager) {
    SettingsSwitch(
        label = stringResource(Res.strings.appearance_monet),
        secondaryLabel = stringResource(Res.strings.appearance_monet_description),
        pref = prefs.monet,
        disabled = Build.VERSION.SDK_INT < Build.VERSION_CODES.S
    ) { prefs.monet = it }
}