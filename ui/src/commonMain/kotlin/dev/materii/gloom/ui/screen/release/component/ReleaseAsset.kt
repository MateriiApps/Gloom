package dev.materii.gloom.ui.screen.release.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.FolderZip
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.domain.manager.DialogManager
import dev.materii.gloom.domain.manager.DialogState
import dev.materii.gloom.ui.icon.Custom
import dev.materii.gloom.ui.icon.custom.IOS
import dev.materii.gloom.ui.screen.release.dialog.ReleaseAssetDownloadDialog
import dev.materii.gloom.ui.util.getFileSizeString
import org.koin.compose.koinInject

@Composable
fun ReleaseAsset(
    name: String,
    size: Int,
    onDownloadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dialogManager: DialogManager = koinInject()
    var showConfirmDialog by remember {
        mutableStateOf(false)
    }
    val icon = when (name.split(".").lastOrNull()?.lowercase()) {
        "apks",
        "apkx",
        "apk" -> Icons.Outlined.Android

        "ipa",
        "tipa" -> Icons.Custom.IOS

        "dmg",
        "msi",
        "appimage",
        "deb",
        "snap",
        "rpm",
        "nupkg",
        "pkg",
        "exe" -> Icons.Outlined.Archive

        "zip",
        "7z",
        "7zip",
        "xz",
        "tar",
        "gz" -> Icons.Outlined.FolderZip

        else -> Icons.AutoMirrored.Outlined.InsertDriveFile
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(9.dp)
                .size(18.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 15.sp
            )

            Text(
                text = getFileSizeString(size),
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = LocalContentColor.current.copy(0.5f)
            )
        }

        if (showConfirmDialog) {
            ReleaseAssetDownloadDialog(
                fileName = name,
                fileSize = size,
                onClose = { showConfirmDialog = false },
                onConfirm = { dontShowAgain ->
                    if (dontShowAgain) dialogManager.downloadAsset = DialogState.CONFIRMED
                    showConfirmDialog = false
                    onDownloadClick()
                }
            )
        }

        IconButton(
            onClick = {
                if (dialogManager.downloadAsset == DialogState.UNKNOWN)
                    showConfirmDialog = true
                else
                    onDownloadClick()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Download,
                contentDescription = stringResource(Res.strings.action_download)
            )
        }
    }
}