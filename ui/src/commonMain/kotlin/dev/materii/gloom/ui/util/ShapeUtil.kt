package dev.materii.gloom.ui.util

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import dev.materii.gloom.domain.manager.enums.AvatarShape

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

/**
 * A rectangle shape where each axis can be resized proportionately
 *
 * @param scaleX Scales the horizontal axis
 * @param scaleY Scales the vertical axis
 */
@Suppress("FunctionName")
fun ScaledRectShape(scaleX: Float = 1f, scaleY: Float = 1f) = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density) =
        Outline.Rectangle(Size(size.width * scaleX, size.height * scaleY).toRect())

    override fun toString(): String = "RectangleShape"
}