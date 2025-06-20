package dev.materii.gloom.ui.screen.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.FeedRepository
import dev.materii.gloom.ui.component.LabeledIcon
import dev.materii.gloom.ui.screen.repo.RepoScreen
import dev.materii.gloom.ui.theme.gloomColorScheme
import dev.materii.gloom.ui.util.NavigationUtil.navigate
import dev.materii.gloom.ui.util.parsedColor
import dev.materii.gloom.util.NumberFormatter
import dev.materii.gloom.util.pluralStringResource

@Composable
fun FeedRepoCard(
    repo: FeedRepository,
    modifier: Modifier = Modifier,
    starData: Pair<Boolean, Int>? = null,
    onStarClick: () -> Unit = {},
    onUnstarClick: () -> Unit = {},
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
        modifier = modifier.fillMaxWidth()
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
            Breadcrumb(
                repoName = repo.name,
                username = repo.owner.login,
                avatarUrl = repo.owner.avatarUrl,
                userTypeName = repo.owner.__typename,
                modifier = Modifier.align(Alignment.Start)
            )

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
                    if (viewerHasStarred) onUnstarClick() else onStarClick()
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