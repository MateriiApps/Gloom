package com.materiiapps.gloom.ui.screen.settings.component.account

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.materiiapps.gloom.Res
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SignOutDialog(
    signedOut: Boolean,
    onSignedOut: () -> Unit,
    onDismiss: () -> Unit,
    onSignOutClick: () -> Unit
) {
    if (signedOut) {
        onSignedOut()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(Res.strings.settings_sign_out_header)) },
        text = { Text(stringResource(Res.strings.settings_sign_out_text)) },
        confirmButton = {
            Button(
                onClick = onSignOutClick
            ) {
                Text(stringResource(Res.strings.action_sign_out))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(Res.strings.dismiss_nevermind))
            }
        }
    )
}