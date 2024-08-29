package com.materiiapps.gloom.ui.component

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun ThinDivider() = HorizontalDivider(
    thickness = 0.5.dp,
    color = MaterialTheme.colorScheme.onSurface.copy(0.1f)
)