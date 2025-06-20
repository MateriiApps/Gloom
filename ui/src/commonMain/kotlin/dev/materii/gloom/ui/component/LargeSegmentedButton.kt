package dev.materii.gloom.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.LargeSegmentedButton(
    icon: Any,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconDescription: String? = null,
    enabled: Boolean = true
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        modifier = modifier
            .clickable(enabled = enabled, role = Role.Button) { onClick() }
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
            .weight(1f)
            .padding(16.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.primary.copy(if (enabled) 1f else 0.5f)
        ) {
            when (icon) {
                is ImageVector -> {
                    Icon(
                        imageVector = icon,
                        contentDescription = iconDescription
                    )
                }

                is Painter     -> {
                    Icon(
                        painter = icon,
                        contentDescription = iconDescription
                    )
                }
            }

            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                modifier = Modifier.basicMarquee(Int.MAX_VALUE)
            )
        }
    }
}

@Composable
fun LargeSegmentedButtonRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) = Row(
    horizontalArrangement = Arrangement.spacedBy(2.dp),
    modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .then(modifier),
    content = content
)