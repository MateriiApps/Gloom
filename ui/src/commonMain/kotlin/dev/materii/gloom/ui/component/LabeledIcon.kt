package dev.materii.gloom.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

/**
 * Simple icon and label pair
 *
 * @param icon The icon to show with the label
 * @param label Label to display
 * @param modifier The [Modifier] for this [LabeledIcon]
 * @param iconTint The color to use for the [icon]
 * @param labelColor The color to use for the [label] text
 */
@Composable
fun LabeledIcon(
    icon: Painter,
    label: String,
    modifier: Modifier = Modifier,
    iconTint: Color = LocalContentColor.current,
    labelColor: Color = LocalContentColor.current.copy(alpha = 0.6f)
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = iconTint
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = labelColor
        )
    }
}

/**
 * Simple icon and label pair
 *
 * @param icon The icon to show with the label
 * @param label Label to display
 * @param modifier The [Modifier] for this [LabeledIcon]
 * @param iconTint The color to use for the [icon]
 * @param labelColor The color to use for the [label] text
 */
@Composable
fun LabeledIcon(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    iconTint: Color = LocalContentColor.current,
    labelColor: Color = LocalContentColor.current.copy(alpha = 0.6f)
) {
    LabeledIcon(
        icon = rememberVectorPainter(icon),
        label = label,
        iconTint = iconTint,
        labelColor = labelColor,
        modifier = modifier
    )
}