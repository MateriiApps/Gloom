package com.materiiapps.gloom.ui.transition

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun SlideTransition(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold
    ),
    content: @Composable AnimatedVisibilityScope.(Screen) -> Unit = { it.Content() }
) {
    AnimatedContent(
        targetState = navigator.lastItem,
        label = "Screen Transition",
        modifier = modifier,
        transitionSpec = {
            val (initialOffset, targetOffset) = when (navigator.lastEvent) {
                StackEvent.Pop -> ({ size: Int -> -size }) to ({ size: Int -> size })
                else -> ({ size: Int -> size }) to ({ size: Int -> -size })
            }

            slideInHorizontally(animationSpec, initialOffset) togetherWith
                    slideOutHorizontally(animationSpec, targetOffset)
        }
    ) { screen ->
        navigator.saveableState("transition", screen) {
            content(screen)
        }
    }
}