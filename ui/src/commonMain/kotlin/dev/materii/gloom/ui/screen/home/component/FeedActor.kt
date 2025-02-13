package dev.materii.gloom.ui.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.materii.gloom.ui.component.BadgedItem
import dev.materii.gloom.ui.util.thenIf
import dev.materii.gloom.util.TimeUtils
import kotlinx.datetime.Instant

@Composable
fun FeedActor(
    iconUrl: String? = null,
    badgeIcon: ImageVector? = null,
    badgeIconDescription: String? = null,
    iconVector: ImageVector? = null,
    iconDescription: String? = null,
    text: AnnotatedString,
    createdAt: Instant? = null,
    onIconClick: (() -> Unit)? = null
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        BadgedItem(badge = if (badgeIcon != null) { ->
            Icon(
                badgeIcon,
                badgeIconDescription,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(12.dp)
                    .padding(0.4.dp)
            )
        } else null) {
            if (iconUrl != null)
                AsyncImage(
                    model = iconUrl,
                    contentDescription = iconDescription,
                    modifier = Modifier
                        .size(33.dp)
                        .clip(CircleShape)
                        .thenIf(onIconClick != null) { clickable(onClick = onIconClick!!) }
                )

            if (iconVector != null)
                Icon(
                    imageVector = iconVector,
                    contentDescription = iconDescription,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .clip(CircleShape)
                        .thenIf(onIconClick != null) { clickable(onClick = onIconClick!!) }
                        .background(MaterialTheme.colorScheme.primary)
                        .size(33.dp)
                        .padding(7.dp)
                )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            createdAt?.let {
                Text(
                    text = TimeUtils.getTimeSince(it),
                    style = MaterialTheme.typography.labelSmall,
                    color = LocalContentColor.current.copy(alpha = 0.5f),
                    softWrap = false
                )
            }
        }
    }
}