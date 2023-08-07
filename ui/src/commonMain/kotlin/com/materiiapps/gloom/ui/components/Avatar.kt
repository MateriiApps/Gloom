package com.materiiapps.gloom.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.materiiapps.gloom.api.dto.user.User
import com.seiko.imageloader.rememberImagePainter

@Composable
fun Avatar(
    url: String?,
    contentDescription: String?,
    type: User.Type = User.Type.USER,
    modifier: Modifier = Modifier
) {
    val painter = rememberImagePainter(url ?: "")
    val shape = when (type) {
        User.Type.ORG -> RoundedCornerShape(31)
        User.Type.USER -> CircleShape
    }

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.then(
            Modifier.clip(shape)
        )
    )
}