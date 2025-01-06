package com.materiiapps.gloom.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.api.dto.user.User
import com.materiiapps.gloom.gql.fragment.FeedRepository
import com.materiiapps.gloom.ui.component.Avatar
import com.materiiapps.gloom.ui.component.LabeledIcon
import com.materiiapps.gloom.ui.screen.repo.RepoScreen
import com.materiiapps.gloom.ui.theme.gloomColorScheme
import com.materiiapps.gloom.ui.util.navigate
import com.materiiapps.gloom.ui.util.parsedColor
import com.materiiapps.gloom.util.NumberFormatter
import com.materiiapps.gloom.util.pluralStringResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun FeedRepoCard(
    repo: FeedRepository,
    starData: Pair<Boolean, Int>? = null,
    onStarPressed: () -> Unit = {},
    onUnstarPressed: () -> Unit = {},
) {
    val nav = LocalNavigator.currentOrThrow
    val viewerHasStarred = starData?.first ?: repo.viewerHasStarred
    val starCount = starData?.second ?: repo.stargazerCount
    val (starColor, starIcon) = if (viewerHasStarred) {
        MaterialTheme.gloomColorScheme.star to Icons.Filled.Star
    } else {
        LocalContentColor.current to Icons.Outlined.StarBorder
    }

    ElevatedCard(
        onClick = { nav.navigate(RepoScreen(repo.owner.login, repo.name)) },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (repo.openGraphImageUrl.startsWith("https://repository-images.githubusercontent.com")) {
            AsyncImage(
                model = repo.openGraphImageUrl,
                null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(2f)
                    .fillMaxWidth()
            )
        }

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
                Avatar(
                    url = repo.owner.avatarUrl,
                    type = User.Type.fromTypeName(repo.owner.__typename),
                    modifier = Modifier.size(20.dp)
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

            repo.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                LabeledIcon(
                    icon = starIcon,
                    label = NumberFormatter.compact(starCount),
                    iconTint = starColor
                )

                repo.primaryLanguage?.let { (color, name) ->
                    LabeledIcon(
                        icon = Icons.Filled.Circle,
                        label = name,
                        iconTint = color?.parsedColor ?: Color.Black
                    )
                }
            }

            LabeledIcon(
                icon = Icons.Outlined.Person,
                label = pluralStringResource(
                    res = Res.plurals.noun_contributors,
                    count = repo.contributorsCount,
                    NumberFormatter.compact(repo.contributorsCount)
                )
            )

            FilledTonalButton(
                onClick = {
                    if (viewerHasStarred) onUnstarPressed() else onStarPressed()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    starIcon,
                    contentDescription = null,
                    tint = starColor
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(
                    if (viewerHasStarred) stringResource(Res.strings.action_unstar) else stringResource(
                        Res.strings.action_star
                    )
                )
            }
        }
    }
}