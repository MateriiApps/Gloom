package com.materiiapps.gloom.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
expect fun GloomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean,
    content: @Composable () -> Unit
)