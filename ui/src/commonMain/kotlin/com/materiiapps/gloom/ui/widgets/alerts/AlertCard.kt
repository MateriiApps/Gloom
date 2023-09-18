package com.materiiapps.gloom.ui.widgets.alerts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun AlertCard(
    title: String?,
    message: String?,
    icon: ImageVector?,
    iconContentDescription: String?,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .clickable(enabled = onClick != null, onClick = {
                    onClick?.invoke()
                })
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = iconContentDescription
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                title?.let {
                    Text(it)
                }

                message?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalContentColor.current.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}