package dev.materii.gloom.ui.screen.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.api.dto.user.User
import dev.materii.gloom.ui.component.Avatar
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import dev.materii.gloom.ui.screen.repo.RepoScreen
import dev.materii.gloom.ui.util.navigate

/**
 * Displays the repository and username separated by a slash
 */
@Composable
fun Breadcrumb(
    repoName: String,
    username: String,
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    userTypeName: String = "User",
) {
    Breadcrumb(
        repoName = repoName,
        username = username,
        modifier = modifier,
        avatarUrl = avatarUrl,
        userType = User.Type.fromTypeName(userTypeName)
    )
}

/**
 * Displays the repository and username separated by a slash
 */
@Composable
fun Breadcrumb(
    repoName: String,
    username: String,
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    userType: User.Type = User.Type.USER,
) {
    val nav = LocalNavigator.currentOrThrow

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (avatarUrl != null) {
            Avatar(
                modifier = Modifier
                    .size(20.dp)
                    .clickable { nav.navigate(ProfileScreen(username)) },
                url = avatarUrl,
                contentDescription = stringResource(Res.strings.noun_users_avatar, username),
                type = userType,
            )
        }

        Text(
            buildAnnotatedString {
                withLink(
                    link = LinkAnnotation.Clickable("username") {
                        nav.navigate(ProfileScreen(username))
                    }
                ) {
                    append(username)
                }
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                    )
                ) {
                    append(" / ")
                }
                withLink(
                    link = LinkAnnotation.Clickable("repository") {
                        nav.navigate(RepoScreen(username, repoName))
                    }
                ) {
                    append(repoName)
                }
            },
            style = MaterialTheme.typography.labelMedium
        )
    }
}