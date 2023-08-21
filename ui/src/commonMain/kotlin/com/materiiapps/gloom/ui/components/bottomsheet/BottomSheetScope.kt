@file:Suppress("INVISIBLE_REFERENCE")

package com.materiiapps.gloom.ui.components.bottomsheet

import androidx.compose.foundation.layout.ColumnScope

class BottomSheetScope(
    columnScope: ColumnScope,
    val animateToDismiss: () -> Unit
)