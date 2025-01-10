package com.materiiapps.gloom.ui.screen.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.api.dto.user.User
import com.materiiapps.gloom.gql.fragment.MergedPullRequestFeedItemFragment
import com.materiiapps.gloom.gql.type.ReactionContent
import com.materiiapps.gloom.ui.component.Avatar
import com.materiiapps.gloom.ui.component.Label
import com.materiiapps.gloom.ui.component.ThinDivider
import com.materiiapps.gloom.ui.icon.Custom
import com.materiiapps.gloom.ui.icon.custom.MergedPullRequest
import com.materiiapps.gloom.ui.screen.profile.ProfileScreen
import com.materiiapps.gloom.ui.screen.repo.RepoScreen
import com.materiiapps.gloom.ui.theme.gloomColorScheme
import com.materiiapps.gloom.ui.util.annotatingStringResource
import com.materiiapps.gloom.ui.util.navigate
import com.materiiapps.gloom.ui.widget.markdown.TruncatedMarkdown
import com.materiiapps.gloom.ui.widget.reaction.ReactionRow
import dev.icerock.moko.resources.compose.pluralStringResource
import dev.icerock.moko.resources.compose.stringResource

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
            }
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp).padding(top = 16.dp)
                    .clickable { navigator.navigate(RepoScreen(repo.owner.login, repo.name)) }
            ) {
                Avatar(
                    url = repo.owner.avatarUrl,
                    type = User.Type.fromTypeName(repo.owner.__typename),
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    buildAnnotatedString {
                        append(repo.owner.login)
                        withStyle(SpanStyle(MaterialTheme.colorScheme.onSurface.copy(0.5f))) {
                            append(" / ")
                        }
                        append(repo.name)
                    },
                    style = MaterialTheme.typography.labelMedium
                )
            }

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