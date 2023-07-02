package com.materiiapps.gloom.ui.widgets.repo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.AsyncImage
import com.materiiapps.gloom.R
import com.materiiapps.gloom.gql.fragment.RepoDetails
import com.materiiapps.gloom.ui.screens.profile.ProfileScreen
import com.materiiapps.gloom.utils.navigate

@Composable
fun ContributorsRow(
    contributors: RepoDetails.Contributors
) {
    val nav = LocalNavigator.current

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.noun_contributors),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = contributors.totalCount.toString(),
                style = MaterialTheme.typography.labelLarge,
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .widthIn(21.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(5.dp, 3.dp)
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
                                R.string.noun_users_avatar,
                                contributor.contributorAvatar.login
                            ),
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(30.dp)
                                .clickable {
                                    nav?.navigate(ProfileScreen(contributor.contributorAvatar.login))
                                }
                        )
                }
            }
        }
    }
}