package dev.materii.gloom.ui.screen.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.pluralStringResource
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.api.dto.user.User
import dev.materii.gloom.gql.fragment.MergedPullRequestFeedItemFragment
import dev.materii.gloom.gql.type.ReactionContent
import dev.materii.gloom.ui.component.Label
import dev.materii.gloom.ui.component.ThinDivider
import dev.materii.gloom.ui.icon.Custom
import dev.materii.gloom.ui.icon.custom.MergedPullRequest
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import dev.materii.gloom.ui.theme.gloomColorScheme
import dev.materii.gloom.ui.util.annotatingStringResource
import dev.materii.gloom.ui.util.navigate
import dev.materii.gloom.ui.widget.markdown.TruncatedMarkdown
import dev.materii.gloom.ui.widget.reaction.ReactionRow

@Composable
fun MergedPullRequestItem(
    item: MergedPullRequestFeedItemFragment,
    onReactionClick: (ReactionContent, Boolean) -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
    val actor = item.actor.actorFragment
    val pullRequest = item.pullRequest
    val baseRepo = pullRequest.baseRepository

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        FeedActor(
            iconUrl = actor.avatarUrl,
            iconDescription = stringResource(Res.strings.noun_users_avatar, actor.login),
            badgeIcon = Icons.Custom.MergedPullRequest,
            badgeIconDescription = stringResource(Res.strings.cd_forked_repo),
            onIconClick = { navigator.navigate(ProfileScreen(actor.login)) },
            text = annotatingStringResource(res = Res.strings.contributed_repo, actor.login, "${baseRepo?.owner?.login}/${baseRepo?.name}") {
                when (it) {
                    "repo",
                    "name" -> SpanStyle(color = MaterialTheme.colorScheme.onSurface)
                    "text" -> SpanStyle(color = MaterialTheme.colorScheme.onSurface.copy(0.7f))
                    else -> null
                }
            },
            createdAt = item.createdAt
        )

        PullRequestCard(pullRequest, onReactionClick)
    }
}

@Composable
fun PullRequestCard(
    pullRequest: MergedPullRequestFeedItemFragment.PullRequest,
    onReactionClick: (ReactionContent, Boolean) -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
    val repo = pullRequest.baseRepository!!

    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Breadcrumb(
                repoName = repo.name,
                username = repo.owner.login,
                avatarUrl = repo.owner.avatarUrl,
                userType = User.Type.fromTypeName(repo.owner.__typename),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(MaterialTheme.colorScheme.onSurface.copy(0.5f))) {
                        append("#${pullRequest.number} ")
                    }

                    append(pullRequest.title)
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Label(
                    text = stringResource(Res.strings.label_pr_merged),
                    icon = Icons.Custom.MergedPullRequest,
                    borderColor = Color.Transparent,
                    fillColor = MaterialTheme.gloomColorScheme.statusPurple,
                    textColor = Color.White
                )

                Text(
                    text = pluralStringResource(
                        resource = Res.plurals.plural_pr_merge_details,
                        quantity = pullRequest.commits.totalCount,
                        pullRequest.mergedBy?.login ?: "ghost",
                        pullRequest.commits.totalCount,
                        pullRequest.baseRefName
                    ),
                    style = MaterialTheme.typography.labelMedium,
                    color = LocalContentColor.current.copy(alpha = 0.5f)
                )
            }

            if (pullRequest.bodyHTML.isNotBlank()) {
                TruncatedMarkdown(
                    html = pullRequest.bodyHTML,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }

            ThinDivider()

            ReactionRow(
                reactions = pullRequest.reactionGroups?.map { it.reaction } ?: emptyList(),
                onReactionClick = onReactionClick,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}