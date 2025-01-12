package dev.materii.gloom.ui.icon.custom

import androidx.compose.material.icons.materialIcon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import dev.materii.gloom.ui.icon.CustomIcons

val CustomIcons.Commit: ImageVector
    get() {
        if (_commit != null) {
            return _commit!!
        }
        _commit = materialIcon(name = "Commit") {
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                fillAlpha = 1.0F,
                strokeAlpha = 1.0F,
                strokeLineWidth = 0.0F,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0F,
                pathFillType = PathFillType.NonZero,
            ) {
                moveTo(16.9F, 11.0F)
                lineTo(16.9F, 11.0F)
                curveToRelative(-0.46F, -2.28F, -2.48F, -4.0F, -4.9F, -4.0F)
                reflectiveCurveToRelative(-4.44F, 1.72F, -4.9F, 4.0F)
                horizontalLineToRelative(0.0F)
                horizontalLineTo(2.0F)
                verticalLineToRelative(2.0F)
                horizontalLineToRelative(5.1F)
                horizontalLineToRelative(0.0F)
                curveToRelative(0.46F, 2.28F, 2.48F, 4.0F, 4.9F, 4.0F)
                reflectiveCurveToRelative(4.44F, -1.72F, 4.9F, -4.0F)
                horizontalLineToRelative(0.0F)
                horizontalLineTo(22.0F)
                verticalLineToRelative(-2.0F)
                horizontalLineTo(16.9F)

                moveTo(12.0F, 15.0F)
                curveToRelative(-1.66F, 0.0F, -3.0F, -1.34F, -3.0F, -3.0F)
                reflectiveCurveToRelative(1.34F, -3.0F, 3.0F, -3.0F)
                reflectiveCurveToRelative(3.0F, 1.34F, 3.0F, 3.0F)
                reflectiveCurveTo(13.66F, 15.0F, 12.0F, 15.0F)
                close()
            }
        }
        return _commit!!
    }

private var _commit: ImageVector? = null