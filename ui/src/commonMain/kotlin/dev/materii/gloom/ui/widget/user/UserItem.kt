package dev.materii.gloom.ui.widget.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.materii.gloom.Res
import dev.materii.gloom.api.model.ModelUser
import dev.materii.gloom.ui.component.Avatar
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import dev.materii.gloom.ui.util.navigate
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun UserItem(
    user: ModelUser,
    card: Boolean = false
) {
    val nav = LocalNavigator.current
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .run {
                if (card) clip(RoundedCornerShape(16.dp)) else this
            }
            .background(if (card) MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp) else Color.Transparent)
            .clickable { user.username?.let { nav?.navigate(ProfileScreen(it)) } }
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(
                url = user.avatar,
                contentDescription = stringResource(
                    Res.strings.noun_users_avatar,
                    user.username ?: "ghost"
                ),
                type = user.type,
                modifier = Modifier.size(40.dp)
            )
            Column {
                if (user.displayName.isNullOrEmpty()) {
                    Text(
                        text = user.username ?: "ghost",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 16.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                } else {
                    Text(
                        text = user.displayName!!,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 16.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = user.username ?: "ghost",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                        ),
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                }
            }
        }
        if (!user.bio.isNullOrBlank()) {
            Text(text = user.bio!!, style = MaterialTheme.typography.labelLarge)
        }
    }
}