package dev.materii.gloom.ui.screen.settings.component

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsSwitch(
    label: String,
    secondaryLabel: String? = null,
    enabled: Boolean = true,
    pref: Boolean,
    onPrefChange: (Boolean) -> Unit,
) {
    SettingsItem(
        text = { Text(text = label) },
        secondaryText = {
            secondaryLabel?.let {
                Text(text = it)
            }
        },
        enabled = enabled,
        onClick = { onPrefChange(!pref) }
    ) {
        VerticalDivider(Modifier.height(32.dp))

        Switch(
            checked = pref,
            enabled = enabled,
            onCheckedChange = { onPrefChange(!pref) }
        )
    }
}