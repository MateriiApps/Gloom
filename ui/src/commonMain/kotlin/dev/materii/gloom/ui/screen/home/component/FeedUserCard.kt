package dev.materii.gloom.ui.screen.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonAddAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.api.dto.user.User
import dev.materii.gloom.gql.fragment.FeedOrg
import dev.materii.gloom.gql.fragment.FeedUser
import dev.materii.gloom.ui.component.Avatar
import dev.materii.gloom.ui.component.LabeledIcon
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import dev.materii.gloom.ui.util.navigate
import dev.materii.gloom.util.NumberFormatter
import dev.materii.gloom.util.pluralStringResource

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
                LabeledIcon(
                    icon = Icons.Outlined.Book,
                    label = pluralStringResource(
                        Res.plurals.repositories,
                        count = repos ?: 0,
                        NumberFormatter.compact(repos ?: 0)
                    )
                )

                followers?.let { followerCount ->
                    LabeledIcon(
                        icon = Icons.Outlined.Person,
                        label = pluralStringResource(
                            Res.plurals.followers,
                            count = followerCount,
                            NumberFormatter.compact(followerCount)
                        )
                    )
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