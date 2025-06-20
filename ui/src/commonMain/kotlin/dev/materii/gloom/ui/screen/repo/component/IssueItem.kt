package dev.materii.gloom.ui.screen.repo.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DoNotDisturb
import androidx.compose.material.icons.outlined.ModeStandby
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.IssueOverview
import dev.materii.gloom.gql.type.IssueStateReason
import dev.materii.gloom.ui.theme.gloomColorScheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun IssueItem(issue: IssueOverview) {
    val (icon, color, titleCDRes) = when (issue.stateReason) {
        IssueStateReason.COMPLETED -> Triple(
            Icons.Outlined.CheckCircle,
            MaterialTheme.gloomColorScheme.statusPurple,
            Res.strings.cd_issue_title_completed
        )

        IssueStateReason.NOT_PLANNED -> Triple(
            Icons.Outlined.DoNotDisturb,
            MaterialTheme.gloomColorScheme.statusGrey,
            Res.strings.cd_issue_title_not_planned
        )

        else -> Triple(
            Icons.Outlined.ModeStandby,
            MaterialTheme.gloomColorScheme.statusGreen,
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
        totalComments = issue.comments.totalCount,
        totalAssigned = issue.assignees.totalCount,
        labels = issue
            .labels
            ?.nodes
            ?.filterNotNull()
            ?.map { it.name to it.color }
            ?.toImmutableList()
            ?: persistentListOf(),
        assignedUsers = issue
            .assignees
            .nodes
            ?.filterNotNull()
            ?.map { it.login to it.avatarUrl }
            ?.toImmutableList()
            ?: persistentListOf()
    )
}