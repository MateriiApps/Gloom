package dev.materii.gloom.ui.widget.alert

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun AlertPopup(
    visible: Boolean,
    title: String?,
    message: String?,
    icon: ImageVector?,
    iconContentDescription: String?,
    position: Alert.Position = Alert.Position.TOP,
    onClick: (() -> Unit)?,
    onDismissed: () -> Unit,
    key: String?,
    offset: IntOffset
) {
    val coroutineScope = rememberCoroutineScope()
    val posRemembered = remember(key) { position }
    val animSpring = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold
    )

    val expandedState = remember {
        MutableTransitionState(false)
    }
    var y by remember(key) {
        mutableStateOf(0f)
    }
    val draggableState = rememberDraggableState {
        y += it
    }
    val (enterAnimation, exitAnimation) = remember(key) {
        when (posRemembered) {
            Alert.Position.TOP -> slideInVertically(animSpring) { -it } to slideOutVertically(
                animSpring
            ) { -it }

            Alert.Position.BOTTOM -> slideInVertically(animSpring) { it } to slideOutVertically(
                animSpring
            ) { it }
        }
    }

    LaunchedEffect(visible) {
        expandedState.targetState = visible
    }

    fun animateYTo(pos: Float, onFinished: () -> Unit = {}) {
        coroutineScope.launch {
            animate(y, pos) { target, _ ->
                y = target
            }
        }.invokeOnCompletion { onFinished() }
    }

    if (expandedState.currentState || expandedState.targetState || !expandedState.isIdle) {
        Popup(
            alignment = if (posRemembered == Alert.Position.TOP) Alignment.TopCenter else Alignment.BottomCenter,
            offset = if (posRemembered == Alert.Position.BOTTOM) offset else IntOffset.Zero
        ) {
            AnimatedVisibility(
                visibleState = expandedState,
                enter = enterAnimation,
                exit = exitAnimation
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    AlertCard(
                        title = title,
                        message = message,
                        icon = icon,
                        iconContentDescription = iconContentDescription,
                        onClick = onClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { IntOffset(0, y.roundToInt()) }
                            .draggable(
                                draggableState,
                                orientation = Orientation.Vertical,
                                onDragStopped = {
                                    if (it.absoluteValue >= 500) {
                                        animateYTo(if (it < 0) -2000f else 2000f) {
                                            onDismissed()
                                        }
                                    } else {
                                        animateYTo(0f)
                                    }
                                }
                            )
                    )
                }
            }
        }
    }
}