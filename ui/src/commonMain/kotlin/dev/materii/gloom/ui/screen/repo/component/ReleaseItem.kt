package dev.materii.gloom.ui.screen.repo.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.ReleaseItem
import dev.materii.gloom.ui.component.Label
import dev.materii.gloom.ui.screen.release.ReleaseScreen
import dev.materii.gloom.ui.theme.DarkGreen
import dev.materii.gloom.util.TimeUtils.getTimeSince
import dev.materii.gloom.util.ifNullOrBlank

@Composable
fun ReleaseItem(
    repoOwner: String,
    repoName: String,
    release: ReleaseItem
) {
    val nav = LocalNavigator.currentOrThrow

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clickable { nav.push(ReleaseScreen(repoOwner, repoName, release.tagName)) }
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = release.name.ifNullOrBlank { release.tagName },
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = getTimeSince(release.createdAt),
                color = LocalContentColor.current.copy(alpha = 0.5f),
                style = MaterialTheme.typography.labelLarge
            )

            if (release.isLatest) {
                Label(
                    text = stringResource(Res.strings.label_latest),
                    textColor = DarkGreen
                )
            }

            if (release.isPrerelease) {
                Label(
                    text = stringResource(Res.strings.label_prerelease),
                    textColor = Color(0xFFFF9800)
                )
            }
        }
    }
}