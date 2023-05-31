package com.materiiapps.gloom.ui.widgets.feed

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.materiiapps.gloom.R
import com.materiiapps.gloom.gql.fragment.NewReleaseItemFragment
import com.materiiapps.gloom.ui.theme.BadgeGreen
import com.materiiapps.gloom.ui.widgets.Markdown
import com.materiiapps.gloom.utils.annotatingStringResource

@Composable
@Suppress("UNUSED_PARAMETER")
fun NewReleaseItem(
    item: NewReleaseItemFragment,
    starData: Pair<Boolean, Int>? = null,
    onVisitPressed: (String) -> Unit = {},
) {
    val actor = item.actor.actorFragment
    val release = item.release

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        FeedActor(
            iconUrl = actor.avatarUrl.toString(),
            iconDescription = stringResource(R.string.noun_users_avatar, actor.login),
            badgeIcon = Icons.Filled.LocalOffer,
            badgeIconDescription = stringResource(R.string.noun_release),
            text = annotatingStringResource(id = R.string.published_release, actor.login) {
                when (it) {
                    "name" -> SpanStyle(color = MaterialTheme.colorScheme.onSurface)
                    "text" -> SpanStyle(color = MaterialTheme.colorScheme.onSurface.copy(0.7f))
                    else -> null
                }
            }
        )

        ReleaseCard(release)
    }
}

@Composable
fun ReleaseCard(
    release: NewReleaseItemFragment.Release
) {
    val repo = release.repository.feedRepository
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = repo.owner.avatarUrl,
                    contentDescription = stringResource(
                        R.string.noun_users_avatar,
                        repo.owner.login
                    ),
                    modifier = Modifier
                        .size(20.dp)
                        .clip(
                            if (repo.owner.__typename == "User") CircleShape else RoundedCornerShape(
                                5.dp
                            )
                        )
                )
                Text(
                    buildAnnotatedString {
                        append(repo.owner.login)
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface.copy(0.5f))) {
                            append(" / ")
                        }
                        append(repo.name)
                    },
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = release.name ?: release.tagName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                if (release.isLatest) {
                    Text(
                        text = stringResource(R.string.adj_latest),
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 10.sp,
                        color = BadgeGreen,
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(1.dp, BadgeGreen, CircleShape)
                            .padding(vertical = 4.dp, horizontal = 6.dp)
                    )
                }
            }

            if (release.descriptionHTML?.toString()?.isNotBlank() == true) {
                Markdown(release.descriptionHTML)
            }
        }

        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
            thickness = 0.5.dp,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            ReleaseDetail(
                icon = Icons.Outlined.LocalOffer,
                iconDescription = stringResource(R.string.noun_tag),
                text = release.tagName
            )
            if (release.tagCommit?.abbreviatedOid?.isNotBlank() == true) {
                ReleaseDetail(
                    icon = painterResource(R.drawable.ic_commit_24),
                    iconDescription = stringResource(R.string.noun_tag),
                    text = release.tagCommit.abbreviatedOid
                )
            }

            Spacer(Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { }
                    .padding(vertical = 3.dp, horizontal = 5.dp)
            ) {
                Text(
                    stringResource(R.string.action_view_details),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
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
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}