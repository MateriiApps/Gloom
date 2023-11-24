package com.materiiapps.gloom.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
actual fun GloomTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val colors = when {
        darkTheme -> darkTheme()
        else -> lightTheme()
    }

    CompositionLocalProvider(
        LocalColors provides colors
    ) {
        MaterialTheme(
            colorScheme = colors.toColorScheme(),
            typography = Typography(),
            shapes = Shapes(),
            content = content
        )
    }
}