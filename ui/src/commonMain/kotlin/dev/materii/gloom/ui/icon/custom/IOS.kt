package dev.materii.gloom.ui.icon.custom

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.materii.gloom.ui.icon.CustomIcons

val CustomIcons.IOS: ImageVector
    get() {
        if (_IOS != null) {
            return _IOS!!
        }
        _IOS = ImageVector.Builder(
            name = "iOS",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 960.0F,
            viewportHeight = 960.0F,
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1f
            ) {
                moveTo(160.0F, 360.0F)
                verticalLineToRelative(-80.0F)
                horizontalLineToRelative(80.0F)
                verticalLineToRelative(80.0F)
                horizontalLineToRelative(-80.0F)

                moveTo(160.0F, 680.0F)
                verticalLineToRelative(-240.0F)
                horizontalLineToRelative(80.0F)
                verticalLineToRelative(240.0F)
                horizontalLineToRelative(-80.0F)

                moveTo(440.0F, 680.0F)
                horizontalLineToRelative(-80.0F)
                quadToRelative(-33.0F, 0.0F, -56.5F, -23.5F)
                reflectiveQuadTo(280.0F, 600.0F)
                verticalLineToRelative(-240.0F)
                quadToRelative(0.0F, -33.0F, 23.5F, -56.5F)
                reflectiveQuadTo(360.0F, 280.0F)
                horizontalLineToRelative(80.0F)
                quadToRelative(33.0F, 0.0F, 56.5F, 23.5F)
                reflectiveQuadTo(520.0F, 360.0F)
                verticalLineToRelative(240.0F)
                quadToRelative(0.0F, 33.0F, -23.5F, 56.5F)
                reflectiveQuadTo(440.0F, 680.0F)

                moveTo(360.0F, 600.0F)
                horizontalLineToRelative(80.0F)
                verticalLineToRelative(-240.0F)
                horizontalLineToRelative(-80.0F)
                verticalLineToRelative(240.0F)

                moveTo(560.0F, 680.0F)
                verticalLineToRelative(-80.0F)
                horizontalLineToRelative(160.0F)
                verticalLineToRelative(-80.0F)
                horizontalLineToRelative(-80.0F)
                quadToRelative(-33.0F, 0.0F, -56.5F, -23.5F)
                reflectiveQuadTo(560.0F, 440.0F)
                verticalLineToRelative(-80.0F)
                quadToRelative(0.0F, -33.0F, 23.5F, -56.5F)
                reflectiveQuadTo(640.0F, 280.0F)
                horizontalLineToRelative(160.0F)
                verticalLineToRelative(80.0F)
                lineTo(640.0F, 360.0F)
                verticalLineToRelative(80.0F)
                horizontalLineToRelative(80.0F)
                quadToRelative(33.0F, 0.0F, 56.5F, 23.5F)
                reflectiveQuadTo(800.0F, 520.0F)
                verticalLineToRelative(80.0F)
                quadToRelative(0.0F, 33.0F, -23.5F, 56.5F)
                reflectiveQuadTo(720.0F, 680.0F)
                lineTo(560.0F, 680.0F)
                close()
            }
        }.build()
        return _IOS!!
    }

@Suppress("ObjectPropertyName")
private var _IOS: ImageVector? = null