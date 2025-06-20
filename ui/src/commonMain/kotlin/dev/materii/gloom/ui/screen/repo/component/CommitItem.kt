package dev.materii.gloom.ui.screen.repo.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.CommitDetails
import dev.materii.gloom.ui.component.AvatarPile
import dev.materii.gloom.ui.component.Label
import dev.materii.gloom.ui.component.StatusIcon
import dev.materii.gloom.ui.screen.repo.dialog.CommitSignatureDialog
import dev.materii.gloom.ui.theme.gloomColorScheme
import dev.materii.gloom.ui.util.annotatingStringResource
import dev.materii.gloom.util.TimeUtils
import kotlinx.collections.immutable.persistentListOf

@Composable
fun CommitItem(
    commit: CommitDetails,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .clickable { /* TODO: Commit details screen */ }
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Headline
            Text(
                text = commit.messageHeadline,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            // Status / Timestamp
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (commit.statusCheckRollup != null) {
                    StatusIcon(
                        status = commit.statusCheckRollup!!.state,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = TimeUtils.getTimeSince(commit.committedDate),
                    style = MaterialTheme.typography.labelLarge,
                    color = LocalContentColor.current.copy(alpha = 0.5f),
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }
        }

        // Author / Committer
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val author = commit.author!!.gitActorDetails
            val committer = commit.committer!!.gitActorDetails
            val isUniqueCommiter = !commit.authoredByCommitter && committer.user != null

            AvatarPile(
                avatars = if (isUniqueCommiter) {
                    persistentListOf(author.avatarUrl, committer.avatarUrl)
                } else {
                    persistentListOf(author.avatarUrl)
                }
            )

            Text(
                text = annotatingStringResource(
                    if (isUniqueCommiter) {
                        Res.strings.commit_authored_by_different
                    } else {
                        Res.strings.commit_authored_by_committer
                    },
                    author.name ?: author.user?.login ?: "ghost",
                    committer.name ?: committer.user?.login ?: "ghost"
                ) {
                    when (it) {
                        "author", "committer" -> SpanStyle(
                            color = LocalContentColor.current,
                            fontWeight = FontWeight.SemiBold
                        )

                        else                  -> null
                    }
                },
                style = MaterialTheme.typography.labelMedium,
                color = LocalContentColor.current.copy(alpha = 0.5f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            val clipboard = LocalClipboardManager.current

            Text(
                text = commit.abbreviatedOid,
                style = MaterialTheme.typography.labelMedium,
                fontFamily = FontFamily.Monospace,
                color = LocalContentColor.current.copy(alpha = 0.5f),
                textAlign = TextAlign.End,
                maxLines = 1,
                modifier = Modifier
                    .clickable {
                        clipboard.setText(AnnotatedString(commit.oid.toString())) // FIXME: Add type conversion for GitObjectID
                    }
                    .padding(vertical = 5.dp)
            )

            if (commit.signature?.commitSignatureDetails?.isValid == true) {
                var signatureDetailsOpened by remember { mutableStateOf(false) }

                Label(
                    text = stringResource(Res.strings.label_verified),
                    textColor = MaterialTheme.gloomColorScheme.statusGreen,
                    modifier = Modifier.clickable {
                        signatureDetailsOpened = true
                    }
                )

                if (signatureDetailsOpened) {
                    CommitSignatureDialog(
                        signature = commit.signature!!.commitSignatureDetails,
                        onDismiss = { signatureDetailsOpened = false }
                    )
                }
            }
        }
    }
}