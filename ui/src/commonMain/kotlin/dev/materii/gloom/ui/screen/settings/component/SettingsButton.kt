package dev.materii.gloom.ui.screen.settings.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isDanger: Boolean = false,
    outlined: Boolean = false
) {
    Box(
        modifier = modifier
            .heightIn(min = 64.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        if (outlined) {
            OutlinedButton(
                onClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = if (isDanger) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = label)
            }
        } else {
            Button(
                onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDanger) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    contentColor = if (isDanger) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = label)
            }
        }
    }
}