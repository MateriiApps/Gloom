package dev.materii.gloom.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.collections.immutable.ImmutableList

@Composable
fun AvatarPile(
    avatars: ImmutableList<String>,
    modifier: Modifier = Modifier,
    maxCount: Int = 3
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy((-8).dp),
        modifier = modifier
    ) {
        avatars.take(maxCount).forEachIndexed { i, avatar ->
            Avatar(
                url = avatar,
                modifier = Modifier
                    .zIndex(maxCount - i.toFloat())
                    .size(20.dp)
            )
        }
    }
}