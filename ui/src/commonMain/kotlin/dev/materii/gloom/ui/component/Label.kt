package dev.materii.gloom.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Label(
    text: String? = null,
    icon: ImageVector? = null,
    textColor: Color,
    borderColor: Color = textColor,
    fillColor: Color = Color.Transparent,
    iconColor: Color = textColor,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .then(modifier)
            .clip(CircleShape)
            .background(fillColor)
            .border(1.dp, borderColor, CircleShape)
            .then(
                if (text == null)
                    Modifier.padding(5.dp)
                else
                    Modifier.padding(vertical = 5.dp, horizontal = 7.dp)
            )
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(12.dp)
            )
        }
        text?.let {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp,
                color = textColor,
                maxLines = 1
            )
        }
    }
}