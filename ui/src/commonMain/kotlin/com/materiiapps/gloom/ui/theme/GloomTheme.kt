package com.materiiapps.gloom.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun GloomTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val (colors, gloomColors) = getColorSchemes(darkTheme, dynamicColor)

    CompositionLocalProvider(
        LocalGloomColorScheme provides gloomColors
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography(),
            shapes = Shapes(),
            content = content
        )
    }
}

/**
 * Retrieves the color schemes to be used based on user settings
 *
 * @param darkTheme Whether or not to use the dark theme variant
 * @param dynamicColor (Android 12+ only) Whether or not to use a dynamic color scheme
 */
@Composable
expect fun getColorSchemes(darkTheme: Boolean, dynamicColor: Boolean): Pair<ColorScheme, GloomColorScheme>