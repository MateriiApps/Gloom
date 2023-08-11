package com.materiiapps.gloom.ui.widgets.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.gql.fragment.StarredFeedItemFragment
import com.materiiapps.gloom.ui.utils.annotatingStringResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun StarredRepoItem(
    item: StarredFeedItemFragment,
    starData: Pair<Boolean, Int>? = null,
    onStarPressed: (String) -> Unit = {},
    onUnstarPressed: (String) -> Unit = {},
) {
    val actor = item.actor.actorFragment
    val repo = item.repository.feedRepository

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        FeedActor(
            iconUrl = actor.avatarUrl,
            iconDescription = stringResource(Res.strings.noun_users_avatar, actor.login),
            badgeIcon = Icons.Filled.Star,
            badgeIconDescription = stringResource(Res.strings.noun_starred),
            text = annotatingStringResource(res = Res.strings.starred_repo, actor.login) {
                when (it) {
                    "name" -> SpanStyle(color = MaterialTheme.colorScheme.onSurface)
                    "text" -> SpanStyle(color = MaterialTheme.colorScheme.onSurface.copy(0.7f))
                    else -> null
                }
            }
        )

        FeedRepoCard(
            repo = repo,
            starData = starData,
            onStarPressed = {
                onStarPressed(repo.id)
            },
            onUnstarPressed = {
                onUnstarPressed(repo.id)
            }
        )
    }
}