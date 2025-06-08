package dev.materii.gloom.ui.screen.repo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.RepoDetails
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import dev.materii.gloom.ui.util.navigate

@Composable
fun ContributorsRow(
    contributors: RepoDetails.Contributors,
    modifier: Modifier = Modifier
) {
    val nav = LocalNavigator.currentOrThrow

    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(Res.strings.title_contributors),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = contributors.totalCount.toString(),
                style = MaterialTheme.typography.labelLarge,
                fontSize = 11.sp,
                lineHeight = 11.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .widthIn(21.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(5.dp, 4.dp)
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            contributors.nodes?.let {
                items(it) { contributor ->
                    if (contributor != null)
                        AsyncImage(
                            model = contributor.contributorAvatar.avatarUrl,
                            contentDescription = stringResource(
                                Res.strings.noun_users_avatar,
                                contributor.contributorAvatar.login
                            ),
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(30.dp)
                                .clickable {
                                    nav.navigate(ProfileScreen(contributor.contributorAvatar.login))
                                }
                        )
                }
            }
        }
    }
}