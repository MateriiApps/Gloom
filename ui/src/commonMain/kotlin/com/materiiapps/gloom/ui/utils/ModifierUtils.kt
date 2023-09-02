package com.materiiapps.gloom.ui.utils

import androidx.compose.ui.Modifier

inline fun Modifier.thenIf(predicate: Boolean, block: Modifier.() -> Modifier)
    = then(if (predicate) block() else Modifier)