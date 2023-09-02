package com.materiiapps.gloom.ui.components.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsButton(
    label: String,
    isDanger: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Button(
            onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if(isDanger) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                contentColor = if(isDanger) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.fillMaxWidth())
        {
            Text(text = label)
        }
    }
}