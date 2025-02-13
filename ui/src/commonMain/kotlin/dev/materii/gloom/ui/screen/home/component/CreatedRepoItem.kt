package dev.materii.gloom.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.CreatedRepoItemFragment
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import dev.materii.gloom.ui.util.annotatingStringResource
import dev.materii.gloom.ui.util.navigate
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun CreatedRepoItem(
    item: CreatedRepoItemFragment,
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
            badgeIcon = Icons.Filled.Book,
            badgeIconDescription = stringResource(Res.strings.noun_repo),
            onIconClick = { navigator.navigate(ProfileScreen(actor.login)) },
            text = annotatingStringResource(res = Res.strings.created_repo, actor.login) {
                when (it) {
                    "name" -> SpanStyle(color = MaterialTheme.colorScheme.onSurface)
                    "text" -> SpanStyle(color = MaterialTheme.colorScheme.onSurface.copy(0.7f))
                    else -> null
                }
            },
            createdAt = item.createdAt
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