package com.materiiapps.gloom.ui.widgets.release

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.FolderZip
import androidx.compose.material.icons.outlined.InsertDriveFile
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materiiapps.gloom.utils.getFileSizeString

@Composable
fun ReleaseAsset(
    name: String,
    size: Int,
    onDownloadClick: () -> Unit
) {
    val icon = when (name.split(".").lastOrNull()?.lowercase()) {
        "apks",
        "apkx",
        "apk" -> Icons.Outlined.Android

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

        else -> Icons.Outlined.InsertDriveFile
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
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
                text = getFileSizeString(size, LocalContext.current),
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = LocalContentColor.current.copy(0.5f)
            )
        }


        IconButton(onClick = onDownloadClick) {
            Icon(
                imageVector = Icons.Filled.Download,
                contentDescription = null
            )
        }
    }
}