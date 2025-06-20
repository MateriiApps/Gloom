package dev.materii.gloom.ui.screen.repo.component

import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.PullRequestOverview
import dev.materii.gloom.gql.type.PullRequestState
import dev.materii.gloom.ui.icon.Custom
import dev.materii.gloom.ui.icon.custom.ClosedPullRequest
import dev.materii.gloom.ui.icon.custom.DraftPullRequest
import dev.materii.gloom.ui.icon.custom.MergedPullRequest
import dev.materii.gloom.ui.icon.custom.OpenPullRequest
import dev.materii.gloom.ui.theme.gloomColorScheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun PullRequestItem(
    pullRequest: PullRequestOverview
) {
    val (icon, color, titleCDRes) = when {
        pullRequest.state == PullRequestState.MERGED -> Triple(
            Icons.Custom.MergedPullRequest,
            MaterialTheme.gloomColorScheme.statusPurple,
            Res.strings.cd_pr_title_merged
        )

        pullRequest.state == PullRequestState.CLOSED -> Triple(
            Icons.Custom.ClosedPullRequest,
            MaterialTheme.gloomColorScheme.statusRed,
            Res.strings.cd_pr_title_closed
        )

        pullRequest.state == PullRequestState.OPEN && pullRequest.isDraft -> Triple(
            Icons.Custom.DraftPullRequest,
            MaterialTheme.gloomColorScheme.statusGrey,
            Res.strings.cd_pr_title_draft
        )

        else -> Triple(
            Icons.Custom.OpenPullRequest,
            MaterialTheme.gloomColorScheme.statusGreen,
            Res.strings.cd_pr_title_opened
        )
    }

    IssueOrPRItem(
        createdAt = pullRequest.createdAt,
        icon = icon,
        color = color,
        titleCDRes = titleCDRes,
        number = pullRequest.number,
        title = if (pullRequest.title == "\u200E") stringResource(Res.strings.msg_issue_untitled) else pullRequest.title,
        authorUsername = pullRequest.author?.login,
        labels = pullRequest
            .labels
            ?.nodes
            ?.filterNotNull()
            ?.map { it.name to it.color }
            ?.toImmutableList()
            ?: persistentListOf(),
        totalAssigned = pullRequest.assignees.totalCount,
        assignedUsers = pullRequest
            .assignees
            .nodes
            ?.filterNotNull()
            ?.map { it.login to it.avatarUrl }
            ?.toImmutableList()
            ?: persistentListOf(),
        totalComments = pullRequest.comments.totalCount,
        checksStatus = pullRequest.commits.nodes?.firstOrNull()?.commit?.statusCheckRollup?.state,
        reviewDecision = pullRequest.reviewDecision
    )
}