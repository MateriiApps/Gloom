package dev.materii.gloom.ui.component.scrollbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.materii.gloom.ui.util.toDp
import dev.materii.gloom.ui.util.toPx
import kotlinx.coroutines.delay

@Composable
fun ScrollBar(
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Vertical,
    thickness: Dp = 6.dp,
    safeAreaPadding: Dp = 6.dp,
    thumbColor: Color = MaterialTheme.colorScheme.tertiary,
    trackColor: Color = Color.Transparent,
    idleOpacity: Float = 0.07f,
    activeOpacity: Float = 0.65f,
    idleCooldownMillis: Long = 750
) {
    var screenSize by remember(orientation) { mutableStateOf(0.dp) }
    val canScroll = scrollState.canScrollBackward || scrollState.canScrollForward
    val totalArea = screenSize.toPx() + scrollState.maxValue
    val scrollBarSizeRatio = screenSize.toPx().toFloat() / totalArea
    val thumbSize = screenSize * scrollBarSizeRatio
    val draggableState = rememberDraggableState {
        scrollState.dispatchRawDelta(it / scrollBarSizeRatio)
    }

    var opacity by remember(idleOpacity) {
        mutableStateOf(idleOpacity)
    }

    // Use a smooth transition for opacity changes
    val opacityAnimated by animateFloatAsState(
        opacity,
        animationSpec = tween(durationMillis = 600),
        label = "Scrollbar Opacity"
    )

    // Increase the opacity when scrolling, and decrease it after a delay
    LaunchedEffect(scrollState.value) {
        opacity = activeOpacity
        delay(idleCooldownMillis)
        opacity = idleOpacity
    }

    // Modifiers
    val trackSizingModifier = Modifier.run {
        when (orientation) {
            Orientation.Horizontal -> fillMaxWidth()
            Orientation.Vertical   -> fillMaxHeight()
        }
    }
    val thumbSizeModifier = Modifier.run {
        when (orientation) {
            Orientation.Horizontal -> size(
                width = thumbSize,
                height = thickness
            )

            Orientation.Vertical   -> size(
                width = thickness,
                height = thumbSize
            )
        }
    }
    val thumbOffsetModifier = Modifier.run {
        val thumbOffset = (scrollState.value.toDp() * scrollBarSizeRatio) - safeAreaPadding
        when (orientation) {
            Orientation.Horizontal -> offset(thumbOffset, 0.dp)
            Orientation.Vertical   -> offset(0.dp, thumbOffset)
        }
    }

    if (canScroll) {
        BoxWithConstraints(
            modifier = modifier
                .then(trackSizingModifier)
                .alpha(opacityAnimated)
                .background(trackColor)
        ) {
            screenSize = when (orientation) {
                Orientation.Horizontal -> maxWidth
                Orientation.Vertical   -> maxHeight
            }

            // Touch target for the thumb
            Box(
                modifier = Modifier
                    .then(thumbOffsetModifier)
//                    .background(Color.Yellow) // Uncomment this to visualize the touch target
                    .padding(safeAreaPadding)
                    .draggable(
                        draggableState,
                        orientation = orientation,
                        startDragImmediately = true
                    )
            ) {
                // Visual element of the thumb
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .then(thumbSizeModifier)
                        .background(thumbColor)
                )
            }
        }
    }
}