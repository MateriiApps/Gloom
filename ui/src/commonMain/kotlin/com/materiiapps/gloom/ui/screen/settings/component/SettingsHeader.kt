package com.materiiapps.gloom.ui.screen.settings.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Header used to denote a set of options within a screen
 *
 * @param text Label to use for this header
 */
@Composable
fun SettingsHeader(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp)
    )
}