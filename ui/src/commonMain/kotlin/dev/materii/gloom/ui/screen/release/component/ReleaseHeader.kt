package dev.materii.gloom.ui.screen.release.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.api.dto.user.User
import dev.materii.gloom.gql.fragment.ReleaseDetails
import dev.materii.gloom.ui.component.Avatar
import dev.materii.gloom.ui.component.Label
import dev.materii.gloom.ui.screen.repo.RepoScreen
import dev.materii.gloom.ui.theme.gloomColorScheme
import dev.materii.gloom.util.ifNullOrBlank

@Composable
fun ReleaseHeader(
    details: ReleaseDetails
) {
    val nav = LocalNavigator.currentOrThrow

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable {
                nav.push(RepoScreen(details.repository.owner.login, details.repository.name))
            }
        ) {
            Avatar(
                url = details.repository.owner.avatarUrl,
                contentDescription = null,
                type = User.Type.fromTypeName(details.repository.owner.__typename),
                modifier = Modifier.size(22.dp)
            )

            Text(
                buildAnnotatedString {
                    append(details.repository.owner.login)
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface.copy(0.5f))) {
                        append(" / ")
                    }
                    append(details.repository.name)
                },
                style = MaterialTheme.typography.labelLarge
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = details.name.ifNullOrBlank { details.tagName },
                maxLines = 1,
                style = MaterialTheme.typography.headlineMedium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false)
            )

            if (details.isLatest) {
                Label(
                    text = stringResource(Res.strings.label_latest),
                    textColor = MaterialTheme.gloomColorScheme.statusGreen
                )
            }

            if (details.isPrerelease) {
                Label(
                    text = stringResource(Res.strings.label_prerelease),
                    textColor = MaterialTheme.gloomColorScheme.statusYellow
                )
            }
        }
    }
}