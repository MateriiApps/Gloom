package com.materiiapps.gloom.ui.screen.release.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.components.Avatar
import com.materiiapps.gloom.ui.screen.profile.ProfileScreen
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ReleaseContributors(
    contributors: List<Pair<String, String>>
) {
    val nav = LocalNavigator.currentOrThrow

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
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