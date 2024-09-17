package com.materiiapps.gloom.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.materiiapps.gloom.util.supportsMonet

@Composable
actual fun getColorSchemes(darkTheme: Boolean, dynamicColor: Boolean): Pair<ColorScheme, GloomColorScheme> {
    // We don't technically need to check for dynamic theming support
    // here because its locked behind a setting that itself is SDK restricted
    // but its good to be cautious anyways.
    return when {
        dynamicColor && darkTheme && supportsMonet -> dynamicDarkColorScheme(LocalContext.current) to darkGloomColorScheme()
        dynamicColor && !darkTheme && supportsMonet -> dynamicLightColorScheme(LocalContext.current) to lightGloomColorScheme()
        darkTheme -> darkColorScheme() to darkGloomColorScheme()
        else -> lightColorScheme() to lightGloomColorScheme()
    }
}