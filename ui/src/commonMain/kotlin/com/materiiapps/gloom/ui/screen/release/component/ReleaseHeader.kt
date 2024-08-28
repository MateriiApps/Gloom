package com.materiiapps.gloom.ui.screen.release.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.api.dto.user.User
import com.materiiapps.gloom.gql.fragment.ReleaseDetails
import com.materiiapps.gloom.ui.components.Avatar
import com.materiiapps.gloom.ui.components.Label
import com.materiiapps.gloom.ui.screen.repo.RepoScreen
import com.materiiapps.gloom.ui.theme.colors
import com.materiiapps.gloom.utils.ifNullOrBlank
import dev.icerock.moko.resources.compose.stringResource

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
                    textColor = MaterialTheme.colors.statusGreen
                )
            }

            if (details.isPrerelease) {
                Label(
                    text = stringResource(Res.strings.label_prerelease),
                    textColor = MaterialTheme.colors.statusYellow
                )
            }
        }
    }
}