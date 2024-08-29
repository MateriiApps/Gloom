package com.materiiapps.gloom.ui.screen.repo.component

import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.gql.fragment.PullRequestOverview
import com.materiiapps.gloom.gql.type.PullRequestState
import com.materiiapps.gloom.ui.icon.custom.ClosedPullRequest
import com.materiiapps.gloom.ui.icon.Custom
import com.materiiapps.gloom.ui.icon.custom.DraftPullRequest
import com.materiiapps.gloom.ui.icon.custom.MergedPullRequest
import com.materiiapps.gloom.ui.icon.custom.OpenPullRequest
import com.materiiapps.gloom.ui.theme.colors
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun PullRequestItem(
    pullRequest: PullRequestOverview
) {
    val (icon, color, titleCDRes) = when {
        pullRequest.state == PullRequestState.MERGED -> Triple(
            Icons.Custom.MergedPullRequest,
            MaterialTheme.colors.statusPurple,
            Res.strings.cd_pr_title_merged
        )

        pullRequest.state == PullRequestState.CLOSED -> Triple(
            Icons.Custom.ClosedPullRequest,
            MaterialTheme.colors.statusRed,
            Res.strings.cd_pr_title_closed
        )

        pullRequest.state == PullRequestState.OPEN && pullRequest.isDraft -> Triple(
            Icons.Custom.DraftPullRequest,
            MaterialTheme.colors.statusGrey,
            Res.strings.cd_pr_title_draft
        )

        else -> Triple(
            Icons.Custom.OpenPullRequest,
            MaterialTheme.colors.statusGreen,
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
        labels = pullRequest.labels?.nodes?.filterNotNull()?.map { it.name to it.color }
            ?: emptyList(),
        totalAssigned = pullRequest.assignees.totalCount,
        assignedUsers = pullRequest.assignees.nodes?.filterNotNull()
            ?.map { it.login to it.avatarUrl } ?: emptyList(),
        totalComments = pullRequest.comments.totalCount,
        checksStatus = pullRequest.commits.nodes?.firstOrNull()?.commit?.statusCheckRollup?.state,
        reviewDecision = pullRequest.reviewDecision
    )
}