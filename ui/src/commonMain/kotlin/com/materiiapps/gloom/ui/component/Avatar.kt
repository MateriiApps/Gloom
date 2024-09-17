package com.materiiapps.gloom.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.materiiapps.gloom.api.dto.user.User
import com.materiiapps.gloom.domain.manager.PreferenceManager
import com.materiiapps.gloom.ui.util.getShapeForPref
import com.seiko.imageloader.rememberImagePainter
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
    val painter = rememberImagePainter(url ?: "")
    val shape = when (type) {
        User.Type.ORG -> getShapeForPref(prefs.orgAvatarShape, prefs.orgAvatarRadius)
        User.Type.USER -> getShapeForPref(prefs.userAvatarShape, prefs.userAvatarRadius)
    }

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier
            .clip(shape)
            .then(modifier)
    )
}