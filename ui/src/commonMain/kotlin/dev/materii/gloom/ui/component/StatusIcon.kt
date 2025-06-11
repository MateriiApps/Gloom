package dev.materii.gloom.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.type.StatusState
import dev.materii.gloom.ui.theme.gloomColorScheme

@Stable
@Composable
fun StatusState.components(): Triple<ImageVector, Color, StringResource> = when (this) {
    StatusState.EXPECTED -> Triple(
        Icons.Outlined.Circle,
        MaterialTheme.colorScheme.surfaceTint,
        Res.strings.label_checks
    )

    StatusState.PENDING  -> Triple(
        Icons.Filled.Circle,
        MaterialTheme.gloomColorScheme.statusYellow,
        Res.strings.label_checks
    )

    StatusState.SUCCESS  -> Triple(
        Icons.Filled.CheckCircle,
        MaterialTheme.gloomColorScheme.statusGreen,
        Res.strings.label_checks
    )

    else                 -> Triple(
        Icons.Filled.Cancel,
        MaterialTheme.colorScheme.error,
        Res.strings.label_checks_failed
    )
}

/**
 * Displays a status icon with a specific color and label based on the [StatusState].
 */
@Stable
@Composable
fun StatusIcon(
    status: StatusState,
    modifier: Modifier = Modifier
) {
    val (statusIcon, statusColor, statusLabelRes) = status.components()

    Icon(
        imageVector = statusIcon,
        contentDescription = stringResource(statusLabelRes),
        tint = statusColor,
        modifier = modifier
            .size(12.dp)
    )
}