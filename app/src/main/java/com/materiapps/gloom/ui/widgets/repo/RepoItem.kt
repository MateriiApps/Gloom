package com.materiapps.gloom.ui.widgets.repo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.materiapps.gloom.R
import com.materiapps.gloom.domain.models.ModelRepo
import com.materiapps.gloom.rest.dto.user.User

@Composable
fun RepoItem(
    repo: ModelRepo,
    card: Boolean = false
) {
    Column(
        Modifier
            .run {
                if (card) {
                    clip(RoundedCornerShape(16.dp))
                } else this
            }
            .background(if (card) MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp) else Color.Transparent)
            .clickable { }
            .padding(16.dp)
            .run {
                if (card) {
                    width(260.dp)
                } else fillMaxWidth()
            },
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        if(repo.owner != null) Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = repo.owner.avatar,
                contentDescription = stringResource(R.string.noun_users_avatar, repo.owner.username ?: "ghost"),
                modifier = Modifier
                    .size(20.dp)
                    .clip(
                        if (repo.owner.type == User.Type.USER)
                            CircleShape
                        else RoundedCornerShape(5.dp)
                    )
            )
            Text(
                text = repo.owner.username ?: "ghost",
                style = MaterialTheme.typography.labelMedium
            )
        }
        Spacer(Modifier)
        Text(
            text = repo.name ?: stringResource(R.string.noun_empty_repo),
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        val desc = if(repo.description == null && card) "" else repo.description
        desc?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                ),
                maxLines = if (card) 1 else 5,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (repo.fork == true && !card) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 5.dp)
            ) {
                Icon(
                    painterResource(R.drawable.ic_fork_24),
                    contentDescription = stringResource(R.string.noun_forked_repo),
                    modifier = Modifier.size(15.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                )
                Text(
                    text = stringResource(R.string.forked_from, repo.parent!!.fullName!!),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                    )
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Star,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color(0xFFF1E05A)
                    )
                    Text(text = repo.stars.toString())
                }


                if (repo.language != null) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Circle,
                            contentDescription = repo.language.name,
                            modifier = Modifier.size(15.dp),
                            tint = repo.language.color
                                ?: MaterialTheme.colorScheme.surfaceVariant
                        )
                        Text(text = repo.language.name)
                    }
                }
            }
        }
    }
}