package com.materiiapps.gloom.ui.widgets.release.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.utils.getFileSizeString
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ReleaseAssetDownloadDialog(
    fileName: String,
    fileSize: Int,
    onClose: () -> Unit,
    onConfirm: (dontShowAgain: Boolean) -> Unit
) {
    var checked by remember {
        mutableStateOf(false)
    }

    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            FilledTonalButton(onClick = { onConfirm(checked) }) {
                Text(stringResource(Res.strings.action_download))
            }
        },
        dismissButton = {
            TextButton(onClick = onClose) {
                Text(stringResource(Res.strings.dismiss_cancel))
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Download,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        },
        title = {
            Text(
                text = stringResource(Res.strings.title_download_confirm),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = stringResource(
                        Res.strings.msg_download_dialog_body,
                        fileName,
                        getFileSizeString(fileSize)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable(role = Role.Checkbox) { checked = !checked }
                        .fillMaxWidth()
                ) {
                    Checkbox(checked = checked, onCheckedChange = { checked = it })
                    Text(stringResource(Res.strings.label_dont_show_again))
                }
            }
        }
    )
}