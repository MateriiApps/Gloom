package com.materiiapps.gloom.ui.components

import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun ThinDivider() = Divider(
    color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
    thickness = 0.5.dp,
)