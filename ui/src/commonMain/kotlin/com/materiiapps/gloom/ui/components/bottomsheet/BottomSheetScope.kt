package com.materiiapps.gloom.ui.components.bottomsheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Stable

@Stable
class BottomSheetScope(
    val columnScope: ColumnScope,
    val animateToDismiss: () -> Unit
)