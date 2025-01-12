package dev.materii.gloom.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage
import dev.materii.gloom.api.dto.user.User
import dev.materii.gloom.domain.manager.PreferenceManager
import dev.materii.gloom.ui.util.getShapeForPref
import org.koin.compose.koinInject

/**
 * An avatar for an org or user
 *
 * @param url A url pointing to the avatar image
 * @param contentDescription Content description to use for the avatar, for accessibility
 * @param type The type of user that the avatar belongs to, determines the avatars shape
 * @param modifier The [Modifier] for this component
 */
@Composable
fun Avatar(
    url: String?,
    contentDescription: String? = null,
    type: User.Type = User.Type.USER,
    modifier: Modifier = Modifier
) {
    val prefs: PreferenceManager = koinInject()
    val shape = when (type) {
        User.Type.ORG -> getShapeForPref(prefs.orgAvatarShape, prefs.orgAvatarRadius)
        User.Type.USER -> getShapeForPref(prefs.userAvatarShape, prefs.userAvatarRadius)
    }

    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = Modifier
            .clip(shape)
            .then(modifier)
    )
}