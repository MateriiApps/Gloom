package com.materiapps.gloom.ui.widgets.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.AsyncImage
import com.materiapps.gloom.R
import com.materiapps.gloom.domain.models.ModelUser
import com.materiapps.gloom.rest.dto.user.User
import com.materiapps.gloom.ui.screens.profile.ProfileScreen
import com.materiapps.gloom.utils.navigate

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
                if(card) clip(RoundedCornerShape(16.dp)) else this
            }
            .background(if(card) MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp) else Color.Transparent)
            .clickable { user.username?.let { nav?.navigate(ProfileScreen(it)) } }
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user.avatar,
                contentDescription = stringResource(
                    R.string.noun_users_avatar,
                    user.username ?: "ghost"
                ),
                modifier = Modifier
                    .size(40.dp)
                    .clip(
                        if (user.type == User.Type.USER) CircleShape else RoundedCornerShape(
                            12.dp
                        )
                    )
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
                        text = user.displayName,
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
            Text(text = user.bio, style = MaterialTheme.typography.labelLarge)
        }
    }
}