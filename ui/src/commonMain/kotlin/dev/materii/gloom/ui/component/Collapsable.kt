package dev.materii.gloom.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import dev.materii.gloom.ui.util.ScaledRectShape
import dev.materii.gloom.ui.util.toDp

/**
 * Column where the [top] component can overlaid over the [under] component based on the [overlapFraction]
 *
 * @param overlapFraction How far the [top] component should overlap [under], must be between 0 and 1
 * @param under The component displayed underneath [top] when [overlapFraction] is 1
 * @param top The component that is always visible, overlays [under] when [overlapFraction] is 1
 */
@Composable
fun Collapsable(
    overlapFraction: Float,
    under: @Composable () -> Unit,
    top: @Composable () -> Unit
) {
    assert(overlapFraction in 0f..1f) { "overlapFraction must be between 0 and 1" }

    var underHeight by remember {
        mutableStateOf(0)
    }
    var topHeight by remember {
        mutableStateOf(0)
    }

    Box(
        modifier = Modifier
            .padding(bottom = underHeight.toDp() * (1 - overlapFraction))
    ) {
        Box(
            modifier = Modifier
                .clip(ScaledRectShape(scaleY = 1 - overlapFraction))
                .onGloballyPositioned {
                    underHeight = it.size.height
                }
        ) {
            under()
        }

        Box(
            modifier = Modifier
                .onGloballyPositioned {
                    topHeight = it.size.height
                }
                .offset(y = underHeight.toDp() * (1 - overlapFraction))
        ) {
            top()
        }
    }
}