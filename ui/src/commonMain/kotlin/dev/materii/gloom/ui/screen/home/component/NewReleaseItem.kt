package dev.materii.gloom.ui.screen.home.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.NewReleaseItemFragment
import dev.materii.gloom.ui.component.ThinDivider
import dev.materii.gloom.ui.icon.Custom
import dev.materii.gloom.ui.icon.custom.Commit
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import dev.materii.gloom.ui.screen.release.ReleaseScreen
import dev.materii.gloom.ui.theme.DarkGreen
import dev.materii.gloom.ui.util.annotatingStringResource
import dev.materii.gloom.ui.util.navigate
import dev.materii.gloom.ui.widget.markdown.TruncatedMarkdown
import dev.materii.gloom.util.ifNullOrBlank

@Composable
@Suppress("UNUSED_PARAMETER")
fun NewReleaseItem(
    item: NewReleaseItemFragment,
    starData: Pair<Boolean, Int>? = null,
    onVisitPressed: (String) -> Unit = {},
    onClickCard: () -> Unit = {}
) {
    val navigator = LocalNavigator.currentOrThrow
    val actor = item.actor.actorFragment
    val release = item.release

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        FeedActor(
            iconUrl = actor.avatarUrl,
            iconDescription = stringResource(Res.strings.noun_users_avatar, actor.login),
            badgeIcon = Icons.Filled.LocalOffer,
            badgeIconDescription = stringResource(Res.strings.noun_release),
            onIconClick = { navigator.navigate(ProfileScreen(actor.login)) },
            text = annotatingStringResource(res = Res.strings.published_release, actor.login) {
                when (it) {
                    "name" -> SpanStyle(color = MaterialTheme.colorScheme.onSurface)
                    "text" -> SpanStyle(color = MaterialTheme.colorScheme.onSurface.copy(0.7f))
                    else -> null
                }
            },
            createdAt = item.createdAt
        )

        ReleaseCard(release)
    }
}

@Composable
fun ReleaseCard(
    release: NewReleaseItemFragment.Release
) {
    val repo = release.repository.feedRepository
    val nav = LocalNavigator.currentOrThrow

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { nav.navigate(ReleaseScreen(repo.owner.login, repo.name, release.tagName)) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Breadcrumb(
                    modifier = Modifier.align(Alignment.CenterStart),
                    repoName = repo.name,
                    username = repo.owner.login,
                    avatarUrl = repo.owner.avatarUrl,
                    userTypeName = repo.owner.__typename
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = release.name.ifNullOrBlank { release.tagName },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                if (release.isLatest) {
                    Text(
                        text = stringResource(Res.strings.label_latest),
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 10.sp,
                        color = DarkGreen,
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(1.dp, DarkGreen, CircleShape)
                            .padding(vertical = 4.dp, horizontal = 6.dp)
                    )
                }
            }

            release.descriptionHTML?.let { description ->
                TruncatedMarkdown(
                    html = description,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }

        ThinDivider()

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                ReleaseDetail(
                    icon = Icons.Outlined.LocalOffer,
                    iconDescription = stringResource(Res.strings.noun_tag),
                    text = release.tagName
                )
                if (release.tagCommit?.abbreviatedOid?.isNotBlank() == true) {
                    ReleaseDetail(
                        icon = Icons.Custom.Commit,
                        iconDescription = stringResource(Res.strings.noun_commit),
                        text = release.tagCommit!!.abbreviatedOid
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        nav.navigate(
                            ReleaseScreen(
                                repo.owner.login,
                                repo.name,
                                release.tagName
                            )
                        )
                    }
                    .padding(vertical = 3.dp, horizontal = 5.dp)
            ) {
                Text(
                    stringResource(Res.strings.action_view_details),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )
                Spacer(Modifier.width(2.dp))
                Icon(Icons.Default.ChevronRight, null)
            }
        }
    }
}

@Composable
fun ReleaseDetail(
    icon: Any,
    iconDescription: String,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (icon) {
            is ImageVector -> Icon(
                imageVector = icon,
                contentDescription = iconDescription,
                modifier = Modifier.size(15.dp)
            )

            is Painter -> Icon(
                painter = icon,
                contentDescription = iconDescription,
                modifier = Modifier.size(15.dp)
            )

            else -> throw IllegalArgumentException("Icon must be either ImageVector or Painter")
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}