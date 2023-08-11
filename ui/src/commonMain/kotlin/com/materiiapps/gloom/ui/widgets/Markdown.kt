package com.materiiapps.gloom.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun Markdown(
    text: String,
    modifier: Modifier = Modifier
)