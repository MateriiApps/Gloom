package com.materiiapps.gloom.ui.component.navbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun RowScope.LongClickableNavBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    iconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    selectedIconColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    labelStyle: TextStyle = MaterialTheme.typography.labelMedium,
    labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    selectedLabelColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    // Used to offset the ripple
    var itemWidth by remember { mutableStateOf(0) }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onLongClick = onLongClick,
                onClick = onClick,
                role = Role.Tab
            )
            .semantics {
                this.selected = selected
            }
            .onSizeChanged {
                itemWidth = it.width
            }
            .height(80.dp)
            .weight(1f)
    ) {
        // Animation for both the pill and label
        val animationProgress: Float by animateFloatAsState(
            label = "selectedAnimation",
            targetValue = if (selected) 1f else 0f,
            animationSpec = tween(100),
        )

        val deltaOffset: Offset
        with(LocalDensity.current) {
            val indicatorWidth = 64.dp.roundToPx()
            deltaOffset = Offset(
                (itemWidth - indicatorWidth).toFloat() / 2,
                12.dp.toPx()
            )
        }
        // Moves the ripple onto the pill as opposed to the whole item
        val offsetInteractionSource = remember(interactionSource, deltaOffset) {
            MappedInteractionSource(interactionSource, deltaOffset)
        }

        Box(
            contentAlignment = Alignment.Center
        ) {
            // Indicator pill
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = animationProgress),
                        shape = CircleShape,
                    )
                    .size(height = 32.dp, width = (64 * animationProgress).dp)
            )

            // Icon
            val clearSemantics = label != null && (alwaysShowLabel || selected)
            Box(modifier = if (clearSemantics) Modifier.clearAndSetSemantics {} else Modifier) {
                CompositionLocalProvider(
                    LocalContentColor provides if (selected) selectedIconColor else iconColor,
                    content = icon
                )
            }

            // Indicator pill ripple
            @Suppress("DEPRECATION_ERROR") // Necessary for rememberRipple bc compose devs stupid
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .indication(offsetInteractionSource, rememberRipple())
                    .size(height = 32.dp, width = 64.dp)
            )
        }

        // Optional label
        if (label != null) {
            Box(
                modifier = Modifier
                    .layoutId("label")
                    .alpha(if (alwaysShowLabel) 1f else animationProgress)
                    .padding(horizontal = 4.dp)
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides if (selected) selectedLabelColor else labelColor
                ) {
                    ProvideTextStyle(labelStyle, content = label)
                }
            }
        }
    }
}