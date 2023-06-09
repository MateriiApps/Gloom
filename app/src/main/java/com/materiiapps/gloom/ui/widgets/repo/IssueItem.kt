package com.materiiapps.gloom.ui.widgets.repo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DoNotDisturb
import androidx.compose.material.icons.outlined.ModeStandby
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.materiiapps.gloom.R
import com.materiiapps.gloom.gql.fragment.IssueOverview
import com.materiiapps.gloom.gql.type.IssueStateReason
import com.materiiapps.gloom.ui.theme.BadgeGreen
import kotlinx.datetime.toInstant

@Composable
fun IssueItem(issue: IssueOverview) {
    val (icon, color, titleCDRes) = when (issue.stateReason) {
        IssueStateReason.COMPLETED -> Triple(
            Icons.Outlined.CheckCircle,
            Color.Magenta,
            R.string.cd_issue_title_completed
        )

        IssueStateReason.NOT_PLANNED -> Triple(
            Icons.Outlined.DoNotDisturb,
            Color.Gray,
            R.string.cd_issue_title_not_planned
        )

        else -> Triple(Icons.Outlined.ModeStandby, BadgeGreen, R.string.cd_issue_title_opened)
    }

    IssueOrPRItem(
        createdAt = (issue.createdAt as String).toInstant(),
        icon = icon,
        color = color,
        titleCDRes = titleCDRes,
        number = issue.number,
        title = if (issue.title == "\u200E") stringResource(R.string.msg_issue_untitled) else issue.title,
        authorUsername = issue.author?.login,
        labels = issue.labels?.nodes?.filterNotNull()?.map { it.name to it.color } ?: emptyList(),
        totalAssigned = issue.assignees.totalCount,
        assignedUsers = issue.assignees.nodes?.filterNotNull()?.map { it.login to it.avatarUrl } ?: emptyList(),
        totalComments = issue.comments.totalCount
    )
}