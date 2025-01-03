package com.materiiapps.gloom.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.gql.fragment.ForkedRepositoryFeedItemFragment
import com.materiiapps.gloom.ui.icon.Custom
import com.materiiapps.gloom.ui.icon.custom.Fork
import com.materiiapps.gloom.ui.screen.profile.ProfileScreen
import com.materiiapps.gloom.ui.util.annotatingStringResource
import com.materiiapps.gloom.ui.util.navigate
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ForkedRepoItem(
    item: ForkedRepositoryFeedItemFragment,
    starData: Pair<Boolean, Int>? = null,
    onStarPressed: (String) -> Unit = {},
    onUnstarPressed: (String) -> Unit = {},
) {
    val navigator = LocalNavigator.currentOrThrow
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
            badgeIcon = Icons.Custom.Fork,
            badgeIconDescription = stringResource(Res.strings.cd_forked_repo),
            onIconClick = { navigator.navigate(ProfileScreen(actor.login)) },
            text = annotatingStringResource(res = Res.strings.forked_repo, actor.login) {
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