package com.materiiapps.gloom.ui.utils

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.materiiapps.gloom.domain.manager.AvatarShape

/**
 * Gets the compose representation of the shape preference
 *
 * @param shapePref The shape stored in settings
 * @param radiusPercent The desired radius, only applies to [AvatarShape.RoundedCorner]
 */
fun getShapeForPref(shapePref: AvatarShape, radiusPercent: Int) = when (shapePref) {
    AvatarShape.RoundedCorner -> RoundedCornerShape(radiusPercent)
    AvatarShape.Circle -> CircleShape
    AvatarShape.Squircle -> Squircle
}