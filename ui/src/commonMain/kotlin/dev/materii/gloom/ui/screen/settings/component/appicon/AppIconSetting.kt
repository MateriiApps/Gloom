package dev.materii.gloom.ui.screen.settings.component.appicon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.domain.manager.enums.AppIcon
import dev.materii.gloom.ui.screen.settings.component.SettingsItem

@Composable
fun AppIconSetting(
    appIcon: AppIcon,
    selected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsItem(
        text = { Text(stringResource(appIcon.iconName)) },
        secondaryText = { Text(stringResource(appIcon.iconDescription)) },
        enabled = !selected,
        onClick = { onSelected() },
        leading = {
            Image(
                painter = painterResource(appIcon.preview),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .shadow(10.dp, CircleShape)
            )
        },
        trailing = {
            if (selected) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null
                )
            }
        },
        modifier = modifier
            .semantics(mergeDescendants = true) {
                role = Role.RadioButton
                this.selected = selected
            }
    )
}