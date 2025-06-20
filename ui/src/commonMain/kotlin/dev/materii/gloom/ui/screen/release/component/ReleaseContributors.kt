package dev.materii.gloom.ui.screen.release.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.ui.component.Avatar
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ReleaseContributors(
    contributors: ImmutableList<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    val nav = LocalNavigator.currentOrThrow

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.width(0.dp))
        contributors.forEach { (username, avatarUrl) ->
            @Suppress("DEPRECATION_ERROR") // Necessary for rememberRipple bc compose devs stupid
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(
                        onClick = {
                            nav.push(ProfileScreen(username))
                        },
                        indication = rememberRipple(bounded = false, radius = 40.dp),
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .width(70.dp)
            ) {
                Avatar(
                    url = avatarUrl,
                    contentDescription = stringResource(Res.strings.noun_users_avatar, username),
                    modifier = Modifier.size(35.dp)
                )
                Text(
                    text = username,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(modifier = Modifier.width(0.dp))
    }
}