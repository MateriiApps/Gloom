package com.materiiapps.gloom.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DividerWithLabel(
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
    labelAlignment: Alignment = Alignment.Center,
    labelSpacing: PaddingValues = PaddingValues(horizontal = 12.dp)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        HorizontalDivider(
            thickness = thickness,
            color = color
        )
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .align(labelAlignment)
                .padding(labelSpacing)
        ) {
            ProvideTextStyle(MaterialTheme.typography.labelLarge) { label() }
        }
    }
}