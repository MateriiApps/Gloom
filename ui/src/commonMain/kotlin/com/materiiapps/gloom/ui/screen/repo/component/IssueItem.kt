package com.materiiapps.gloom.ui.screen.repo.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DoNotDisturb
import androidx.compose.material.icons.outlined.ModeStandby
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.gql.fragment.IssueOverview
import com.materiiapps.gloom.gql.type.IssueStateReason
import com.materiiapps.gloom.ui.theme.colors
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun IssueItem(issue: IssueOverview) {
    val (icon, color, titleCDRes) = when (issue.stateReason) {
        IssueStateReason.COMPLETED -> Triple(
            Icons.Outlined.CheckCircle,
            MaterialTheme.colors.statusPurple,
            Res.strings.cd_issue_title_completed
        )

        IssueStateReason.NOT_PLANNED -> Triple(
            Icons.Outlined.DoNotDisturb,
            MaterialTheme.colors.statusGrey,
            Res.strings.cd_issue_title_not_planned
        )

        else -> Triple(
            Icons.Outlined.ModeStandby,
            MaterialTheme.colors.statusGreen,
            Res.strings.cd_issue_title_opened
        )
    }

    IssueOrPRItem(
        createdAt = issue.createdAt,
        icon = icon,
        color = color,
        titleCDRes = titleCDRes,
        number = issue.number,
        title = if (issue.title == "\u200E") stringResource(Res.strings.msg_issue_untitled) else issue.title,
        authorUsername = issue.author?.login,
        labels = issue.labels?.nodes?.filterNotNull()?.map { it.name to it.color } ?: emptyList(),
        totalAssigned = issue.assignees.totalCount,
        assignedUsers = issue.assignees.nodes?.filterNotNull()?.map { it.login to it.avatarUrl }
            ?: emptyList(),
        totalComments = issue.comments.totalCount
    )
}