package com.materiiapps.gloom.ui.widgets.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.R
import com.materiiapps.gloom.gql.fragment.ForkedRepositoryFeedItemFragment
import com.materiiapps.gloom.utils.annotatingStringResource

@Composable
fun ForkedRepoItem(
    item: ForkedRepositoryFeedItemFragment,
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
            iconUrl = actor.avatarUrl.toString(),
            iconDescription = stringResource(R.string.noun_users_avatar, actor.login),
            badgePainter = painterResource(R.drawable.ic_fork_24),
            badgeIconDescription = stringResource(R.string.noun_forked_repo),
            text = annotatingStringResource(id = R.string.forked_repo, actor.login) {
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