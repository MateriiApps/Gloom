package dev.materii.gloom.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A full-width banner that displays text
 *
 * @param text The message to display
 * @param modifier The [Modifier] for this [TextBanner]
 * @param icon Optional icon to display next to the [text]
 * @param backgroundColor Color used for the background of the banner
 * @param contentColor Color used for the text and icon tint
 * @param outlineColor Color to use for the top and bottom borders
 */
@Composable
fun TextBanner(
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    outlineColor: Color = MaterialTheme.colorScheme.secondary
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(ButtonDefaults.IconSpacing, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(outlineColor)
            .padding(vertical = 0.5.dp)
            .background(backgroundColor)
            .padding(12.dp)
    ) {
        icon?.let {
            CompositionLocalProvider(
                LocalContentColor provides contentColor
            ) {
                it()
            }
        }

        ProvideTextStyle(
            MaterialTheme.typography.labelLarge.copy(
                color = contentColor
            )
        ) {
            text()
        }
    }
}