package com.materiiapps.gloom.ui.screen.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonAddAlt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.api.dto.user.User
import com.materiiapps.gloom.gql.fragment.FeedOrg
import com.materiiapps.gloom.gql.fragment.FeedUser
import com.materiiapps.gloom.ui.components.Avatar
import com.materiiapps.gloom.ui.screen.profile.ProfileScreen
import com.materiiapps.gloom.ui.utils.navigate
import com.materiiapps.gloom.ui.utils.pluralStringResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
@Suppress("LocalVariableName")
fun FeedUserCard(
    user: Pair<FeedUser?, FeedOrg?>,
    followData: Pair<Boolean, Int>? = null,
    onFollowPressed: () -> Unit = {},
    onUnfollowPressed: () -> Unit = {},
) {
    val (_user, org) = user
    val viewerIsFollowing = followData?.first ?: _user?.viewerIsFollowing
    val nav = LocalNavigator.current

    val type = if (_user != null) User.Type.USER else User.Type.ORG
    val login = _user?.login ?: org?.login
    val name = _user?.name ?: org?.name
    val bio = _user?.bio ?: org?.description
    val avatarUrl = _user?.avatarUrl ?: org?.avatarUrl
    val repos = _user?.repositories?.totalCount ?: org?.repositories?.totalCount
    val followers = followData?.second ?: _user?.followers?.totalCount

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .clickable {
                    if (login != null) nav?.navigate(ProfileScreen(login))
                }
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Avatar(
                    url = avatarUrl,
                    contentDescription = stringResource(
                        Res.strings.noun_users_avatar,
                        login ?: "ghost"
                    ),
                    type = type,
                    modifier = Modifier.size(47.dp)
                )

                Column {
                    if (name != null) {
                        Text(
                            name,
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 17.sp
                        )
                        Text(
                            login ?: "ghost",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                        )
                    } else {
                        Text(
                            login ?: "ghost",
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 17.sp
                        )
                    }
                }
            }

            if (!bio.isNullOrBlank()) {
                Text(
                    text = bio,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Book,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            pluralStringResource(
                                Res.plurals.repositories,
                                count = repos ?: 0,
                                repos ?: 0
                            )
                        )
                    }

                    if (followers != null) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.Person,
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(stringResource(Res.strings.noun_follower_count, followers))
                        }
                    }
                }
            }

            if (_user?.isViewer != true) FilledTonalButton(
                onClick = {
                    if (viewerIsFollowing != true) onFollowPressed() else onUnfollowPressed()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Outlined.PersonAddAlt,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(
                    if (viewerIsFollowing == true) stringResource(Res.strings.action_unfollow) else stringResource(
                        Res.strings.action_follow
                    )
                )
            }
        }
    }
}