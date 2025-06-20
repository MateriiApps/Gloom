package dev.materii.gloom.ui.util

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

object DimenUtil {

    val navBarPadding: Dp
        @Composable get() = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()

    fun PaddingValues.multiply(multiplier: Float, layoutDirection: LayoutDirection): PaddingValues {
        return PaddingValues(
            start = calculateStartPadding(layoutDirection) * multiplier,
            end = calculateEndPadding(layoutDirection) * multiplier,
            top = calculateTopPadding() * multiplier,
            bottom = calculateBottomPadding() * multiplier
        )
    }

}

@Composable
fun Dp.toPx() = with(LocalDensity.current) {
    toPx().toInt()
}

@Composable
fun Int.toDp() = with(LocalDensity.current) {
    toDp()
}