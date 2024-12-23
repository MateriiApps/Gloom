package com.materiiapps.gloom.ui.icon.social

import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.ui.icon.SocialIcons

val SocialIcons.NPM: ImageVector
    get() {
        if (_NPM != null) {
            return _NPM!!
        }
        _NPM = ImageVector.Builder(
            name = "NPM",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 1024.0F,
            viewportHeight = 1024.0F,
        ).materialPath {
            moveTo(171.3F, 852.7F)
            lineTo(171.3F, 171.3F)
            lineTo(852.7F, 171.3F)
            lineTo(852.7F, 852.7F)
            lineTo(171.3F, 852.7F)

            moveTo(299.0F, 299.0F)
            lineTo(299.0F, 725.0F)
            lineTo(512.0F, 725.0F)
            lineTo(512.0F, 384.2F)
            lineTo(639.8F, 384.2F)
            lineTo(639.8F, 725.0F)
            lineTo(725.0F, 725.0F)
            lineTo(725.0F, 299.0F)
            lineTo(299.0F, 299.0F)
            close()
        }.build()
        return _NPM!!
    }

private var _NPM: ImageVector? = null