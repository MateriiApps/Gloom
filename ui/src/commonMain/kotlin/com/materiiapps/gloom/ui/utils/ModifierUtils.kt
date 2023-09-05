package com.materiiapps.gloom.ui.utils

import androidx.compose.ui.Modifier

inline fun Modifier.thenIf(predicate: Boolean, block: Modifier.() -> Modifier) =
    if (predicate) then(block()) else this