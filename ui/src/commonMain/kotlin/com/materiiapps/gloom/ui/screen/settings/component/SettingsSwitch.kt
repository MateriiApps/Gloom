package com.materiiapps.gloom.ui.screen.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsSwitch(
    label: String,
    secondaryLabel: String? = null,
    disabled: Boolean = false,
    pref: Boolean,
    onPrefChange: (Boolean) -> Unit,
) {
    SettingItem(
        modifier = Modifier.clickable(enabled = !disabled) { onPrefChange(!pref) },
        text = { Text(text = label) },
        secondaryText = {
            secondaryLabel?.let {
                Text(text = it)
            }
        }
    ) {
        Switch(
            checked = pref,
            enabled = !disabled,
            onCheckedChange = { onPrefChange(!pref) }
        )
    }
}