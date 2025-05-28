package dev.materii.gloom.ui.screen.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SettingsCategoryIcon(
    imageVector: ImageVector,
    tint: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    background: Color = MaterialTheme.colorScheme.tertiaryContainer
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = tint,
        modifier = Modifier
            .background(background, CircleShape)
            .padding(8.dp)
            .size(20.dp)
    )
}