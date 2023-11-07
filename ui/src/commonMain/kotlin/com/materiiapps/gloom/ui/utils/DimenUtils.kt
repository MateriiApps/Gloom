package com.materiiapps.gloom.ui.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

object DimenUtils {

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