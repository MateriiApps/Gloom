package com.materiiapps.gloom.ui.widgets.repo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DoNotDisturb
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.outlined.ModeStandby
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.materiiapps.gloom.R
import com.materiiapps.gloom.gql.fragment.IssueOverview
import com.materiiapps.gloom.gql.type.IssueStateReason
import com.materiiapps.gloom.ui.components.Label
import com.materiiapps.gloom.ui.theme.BadgeGreen
import com.materiiapps.gloom.utils.TimeUtils.getTimeSince
import com.materiiapps.gloom.utils.parsedColor
import kotlinx.datetime.toInstant

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IssueCard(
    issue: IssueOverview
) {
    val ctx = LocalContext.current
    val createdAt = (issue.createdAt as String).toInstant()
    val (icon, color, titleCDRes) = when (issue.stateReason) {
        IssueStateReason.COMPLETED -> Triple(
            Icons.Outlined.CheckCircle,
            Color.Magenta,
            R.string.cd_issue_title_completed
        )

        IssueStateReason.NOT_PLANNED -> Triple(
            Icons.Outlined.DoNotDisturb,
            Color.LightGray,
            R.string.cd_issue_title_not_planned
        )

        else -> Triple(Icons.Outlined.ModeStandby, BadgeGreen, R.string.cd_issue_title_opened)
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            )
            .semantics(
                mergeDescendants = true
            ) {
                this.contentDescription = ctx.getString(titleCDRes, issue.number, issue.title)
            }
    ) {
        Icon(
            imageVector = icon,
            tint = color,
            contentDescription = null
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = if (issue.title == "\u200E") stringResource(R.string.msg_issue_untitled) else issue.title,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(
                    R.string.msg_issue_or_pr_author,
                    issue.number,
                    issue.author?.login ?: "ghost"
                ),
                style = MaterialTheme.typography.labelLarge,
                color = LocalContentColor.current.copy(alpha = 0.5f)
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                modifier = Modifier.fillMaxWidth()
            ) {
                issue.labels?.nodes?.filterNotNull()?.let {
                    if (it.isNotEmpty()) {
                        it.forEach { label ->
                            val labelColor = label.color.parsedColor
                            Label(
                                text = label.name,
                                textColor = if (labelColor.luminance() > 0.5f) Color.Black else Color.White,
                                fillColor = labelColor,
                                borderColor = labelColor
                            )
                        }
                    }
                }

                if (issue.assignees.totalCount > 0) {
                    val additionalAssignees = issue.assignees.totalCount - 1
                    val assignee = issue.assignees.nodes?.first()!!

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        AsyncImage(
                            model = assignee.avatarUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(23.dp)
                                .clip(CircleShape)
                        )
                        if (additionalAssignees > 0) {
                            Text(
                                text = "+ $additionalAssignees",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, end = 7.dp)
                            )
                        }
                    }
                }

                Label(
                    text = issue.comments.totalCount.toString(),
                    icon = Icons.Outlined.ModeComment,
                    textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    fillColor = MaterialTheme.colorScheme.primaryContainer,
                    borderColor = MaterialTheme.colorScheme.primaryContainer
                )
            }
        }

        Text(
            text = ctx.getTimeSince(createdAt),
            style = MaterialTheme.typography.labelLarge,
            color = LocalContentColor.current.copy(alpha = 0.5f)
        )
    }
}