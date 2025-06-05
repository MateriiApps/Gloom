package dev.materii.gloom.ui.screen.repo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import dev.icerock.moko.resources.StringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.RepoCommitsQuery
import dev.materii.gloom.gql.fragment.CommitDetails
import dev.materii.gloom.ui.component.AvatarPile
import dev.materii.gloom.ui.component.StatusIcon
import dev.materii.gloom.ui.screen.list.base.BaseListScreen
import dev.materii.gloom.ui.screen.repo.viewmodel.RepoCommitsViewModel
import dev.materii.gloom.ui.util.annotatingStringResource
import dev.materii.gloom.util.TimeUtils
import org.koin.core.parameter.parametersOf

class CommitsScreen(
    private val id: String,
    private val branch: String
) : BaseListScreen<CommitDetails, RepoCommitsQuery.Data?, RepoCommitsViewModel>() {

    override val titleRes: StringResource get() = Res.strings.title_commits
    override val key: ScreenKey
        get() = "${this::class.simpleName}($id, $branch)"
    override val viewModel: RepoCommitsViewModel
        @Composable get() = koinScreenModel { parametersOf(id, branch) }

    @Composable
    override fun Item(item: CommitDetails) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .clickable {
                    // TODO: Navigate to commit details screen
                }
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.messageHeadline,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val author = item.author!!.gitActorDetails
                    val committer = item.committer!!.gitActorDetails
                    val isUniqueCommiter = !item.authoredByCommitter && committer.user != null

                    AvatarPile(
                        avatars = if (isUniqueCommiter) {
                            listOf(author.avatarUrl, committer.avatarUrl)
                        } else {
                            listOf(author.avatarUrl)
                        }
                    )

                    Text(
                        text = annotatingStringResource(
                            if (isUniqueCommiter) {
                                Res.strings.commit_authored_by_different
                            } else {
                                Res.strings.commit_authored_by_committer
                            },
                            author.name ?: author.user?.login ?: "Unknown",
                            committer.name ?: committer.user?.login ?: "Unknown"
                        ) {
                            when (it) {
                                "author", "committer" -> SpanStyle(
                                    color = LocalContentColor.current,
                                    fontWeight = FontWeight.SemiBold
                                )

                                else -> null
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = LocalContentColor.current.copy(alpha = 0.5f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (item.statusCheckRollup != null) {
                    StatusIcon(
                        status = item.statusCheckRollup!!.state,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = TimeUtils.getTimeSince(item.committedDate),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }
        }
    }
}