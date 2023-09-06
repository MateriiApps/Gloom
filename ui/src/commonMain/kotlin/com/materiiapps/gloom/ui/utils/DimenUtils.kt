package com.materiiapps.gloom.ui.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

object DimenUtils {

    val navBarPadding: Dp
        @Composable get() = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()

}