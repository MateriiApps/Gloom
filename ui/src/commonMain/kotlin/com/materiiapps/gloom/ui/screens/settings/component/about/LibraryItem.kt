package com.materiiapps.gloom.ui.screens.settings.component.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Balance
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.components.Label
import com.materiiapps.gloom.ui.theme.colors
import com.materiiapps.gloom.ui.utils.contentDescription
import com.materiiapps.gloom.utils.LocalLinkHandler
import com.materiiapps.gloom.utils.author
import com.mikepenz.aboutlibraries.entity.Library
import dev.icerock.moko.resources.compose.stringResource

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun LibraryItem(
    library: Library
) {
    val linkHandler = LocalLinkHandler.current

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .clickable(enabled = library.website != null) {
                linkHandler.openLink(library.website!!)
            }
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = library.name,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        if (!library.description.isNullOrBlank()) {
            Text(
                text = library.description!!,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp,
                color = LocalContentColor.current.copy(alpha = 0.7f)
            )
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(top = 4.dp)
        ) {
            val labelColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)

            library.artifactVersion?.let {
                Label(
                    text = "v$it",
                    icon = Icons.Outlined.Info,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    borderColor = labelColor,
                    fillColor = labelColor
                )
            }

            if (library.author.isNotBlank()) {
                Label(
                    text = library.author,
                    icon = Icons.Outlined.Person,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    borderColor = labelColor,
                    fillColor = labelColor,
                    modifier = Modifier.contentDescription(
                        Res.strings.cd_library_author,
                        library.author,
                        mergeDescendants = true
                    )
                )
            }

            library.licenses.forEach { license ->
                Label(
                    text = license.name,
                    icon = Icons.Outlined.Balance,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    borderColor = labelColor,
                    fillColor = labelColor,
                    modifier = Modifier.contentDescription(
                        Res.strings.cd_library_license,
                        license.name,
                        mergeDescendants = true
                    )
                )
            }

            key(library) {
                val (label, color, icon) = if (library.openSource)
                    Triple(
                        Res.strings.label_open_source,
                        MaterialTheme.colors.statusGreen,
                        Icons.Filled.CheckCircle
                    )
                else
                    Triple(
                        Res.strings.label_closed_source,
                        MaterialTheme.colors.statusRed,
                        Icons.Filled.Cancel
                    )

                Label(
                    text = stringResource(label),
                    icon = icon,
                    textColor = Color.White,
                    borderColor = color,
                    fillColor = color
                )
            }
        }
    }
}