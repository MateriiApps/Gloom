package com.materiiapps.gloom.ui.widgets.repo

import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.materiiapps.gloom.R
import com.materiiapps.gloom.gql.fragment.PullRequestOverview
import com.materiiapps.gloom.gql.type.PullRequestState
import com.materiiapps.gloom.ui.icons.ClosedPullRequest
import com.materiiapps.gloom.ui.icons.Custom
import com.materiiapps.gloom.ui.icons.DraftPullRequest
import com.materiiapps.gloom.ui.icons.MergedPullRequest
import com.materiiapps.gloom.ui.icons.OpenPullRequest
import com.materiiapps.gloom.ui.theme.colors

@Composable
fun PullRequestItem(
    pullRequest: PullRequestOverview
) {
    val (icon, color, titleCDRes) = when {
        pullRequest.state == PullRequestState.MERGED -> Triple(
            Icons.Custom.MergedPullRequest,
            MaterialTheme.colors.statusPurple,
            R.string.cd_pr_title_merged
        )

        pullRequest.state == PullRequestState.CLOSED -> Triple(
            Icons.Custom.ClosedPullRequest,
            MaterialTheme.colors.statusRed,
            R.string.cd_pr_title_closed
        )

        pullRequest.state == PullRequestState.OPEN && pullRequest.isDraft -> Triple(
            Icons.Custom.DraftPullRequest,
            MaterialTheme.colors.statusGrey,
            R.string.cd_pr_title_draft
        )

        else -> Triple(Icons.Custom.OpenPullRequest, MaterialTheme.colors.statusGreen, R.string.cd_pr_title_opened)
    }

    IssueOrPRItem(
        createdAt = pullRequest.createdAt,
        icon = icon,
        color = color,
        titleCDRes = titleCDRes,
        number = pullRequest.number,
        title = if (pullRequest.title == "\u200E") stringResource(R.string.msg_issue_untitled) else pullRequest.title,
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