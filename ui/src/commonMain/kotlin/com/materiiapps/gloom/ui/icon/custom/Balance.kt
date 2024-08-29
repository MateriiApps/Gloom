package com.materiiapps.gloom.ui.icon.custom

import androidx.compose.material.icons.materialIcon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import com.materiiapps.gloom.ui.icon.CustomIcons

val CustomIcons.Balance: ImageVector
    get() {
        if (_balance != null) {
            return _balance!!
        }
        _balance = materialIcon(name = "Balance") {
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
                moveTo(13.0F, 7.83F)
                curveToRelative(0.85F, -0.3F, 1.53F, -0.98F, 1.83F, -1.83F)
                horizontalLineTo(18.0F)
                lineToRelative(-3.0F, 7.0F)
                curveToRelative(0.0F, 1.66F, 1.57F, 3.0F, 3.5F, 3.0F)
                reflectiveCurveToRelative(3.5F, -1.34F, 3.5F, -3.0F)
                lineToRelative(-3.0F, -7.0F)
                horizontalLineToRelative(2.0F)
                verticalLineTo(4.0F)
                horizontalLineToRelative(-6.17F)
                curveTo(14.42F, 2.83F, 13.31F, 2.0F, 12.0F, 2.0F)
                reflectiveCurveTo(9.58F, 2.83F, 9.17F, 4.0F)
                lineTo(3.0F, 4.0F)
                verticalLineToRelative(2.0F)
                horizontalLineToRelative(2.0F)
                lineToRelative(-3.0F, 7.0F)
                curveToRelative(0.0F, 1.66F, 1.57F, 3.0F, 3.5F, 3.0F)
                reflectiveCurveTo(9.0F, 14.66F, 9.0F, 13.0F)
                lineTo(6.0F, 6.0F)
                horizontalLineToRelative(3.17F)
                curveToRelative(0.3F, 0.85F, 0.98F, 1.53F, 1.83F, 1.83F)
                verticalLineTo(19.0F)
                horizontalLineTo(2.0F)
                verticalLineToRelative(2.0F)
                horizontalLineToRelative(20.0F)
                verticalLineToRelative(-2.0F)
                horizontalLineToRelative(-9.0F)
                verticalLineTo(7.83F)

                moveTo(20.37F, 13.0F)
                horizontalLineToRelative(-3.74F)
                lineToRelative(1.87F, -4.36F)
                lineTo(20.37F, 13.0F)

                moveTo(7.37F, 13.0F)
                horizontalLineTo(3.63F)
                lineTo(5.5F, 8.64F)
                lineTo(7.37F, 13.0F)

                moveTo(12.0F, 6.0F)
                curveToRelative(-0.55F, 0.0F, -1.0F, -0.45F, -1.0F, -1.0F)
                curveToRelative(0.0F, -0.55F, 0.45F, -1.0F, 1.0F, -1.0F)
                reflectiveCurveToRelative(1.0F, 0.45F, 1.0F, 1.0F)
                curveTo(13.0F, 5.55F, 12.55F, 6.0F, 12.0F, 6.0F)
                close()
            }
        }
        return _balance!!
    }

private var _balance: ImageVector? = null