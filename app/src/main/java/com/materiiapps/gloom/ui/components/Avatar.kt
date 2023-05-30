package com.materiiapps.gloom.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage
import com.materiiapps.gloom.domain.models.ModelUser
import com.materiiapps.gloom.rest.dto.user.User

@Composable
fun Avatar(
    url: String?,
    contentDescription: String?,
    type: User.Type,
    modifier: Modifier = Modifier
) {
    val shape = when(type) {
        User.Type.ORG -> RoundedCornerShape(31)
        User.Type.USER -> CircleShape
    }

    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier.then(
            Modifier.clip(shape)
        )
    )
}