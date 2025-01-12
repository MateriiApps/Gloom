package dev.materii.gloom.ui.component.bottomsheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Stable

@Stable
class BottomSheetScope(
    val columnScope: ColumnScope,
    val animateToDismiss: () -> Unit
)