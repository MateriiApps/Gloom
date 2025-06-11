package dev.materii.gloom.ui.icon.social

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.materii.gloom.ui.icon.SocialIcons

val SocialIcons.Facebook: ImageVector
    get() {
        if (_facebook != null) {
            return _facebook!!
        }
        _facebook = ImageVector.Builder(
            name = "Facebook",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 1024.0F,
            viewportHeight = 1024.0F,
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            fillAlpha = 0.0F,
            strokeAlpha = 1.0F,
            strokeLineWidth = 0.0F,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 4.0F,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(0.0F, 0.0F)
            horizontalLineToRelative(1024.0F)
            verticalLineToRelative(1024.0F)
            horizontalLineToRelative(-1024.0F)
            close()
        }.path(
            fill = SolidColor(Color(0xFFFFFFFF)),
            fillAlpha = 1.0F,
            strokeAlpha = 1.0F,
            strokeLineWidth = 0.0F,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 4.0F,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(780.6F, 183.1F)
            lineTo(243.4F, 183.1F)
            curveTo(210.5F, 183.1F, 183.1F, 210.5F, 183.1F, 243.4F)
            lineTo(183.1F, 780.6F)
            curveTo(183.1F, 813.5F, 210.5F, 840.9F, 243.4F, 840.9F)
            lineTo(512.0F, 840.9F)
            lineTo(512.0F, 566.8F)
            lineTo(429.8F, 566.8F)
            lineTo(429.8F, 484.6F)
            lineTo(512.0F, 484.6F)
            lineTo(512.0F, 402.4F)
            curveTo(512.0F, 333.8F, 539.4F, 292.7F, 621.6F, 292.7F)
            lineTo(703.9F, 292.7F)
            lineTo(703.9F, 375.0F)
            lineTo(668.2F, 375.0F)
            curveTo(643.6F, 375.0F, 621.6F, 396.9F, 621.6F, 421.5F)
            lineTo(621.6F, 484.6F)
            lineTo(731.3F, 484.6F)
            lineTo(717.6F, 566.8F)
            lineTo(621.6F, 566.8F)
            lineTo(621.6F, 840.9F)
            lineTo(780.6F, 840.9F)
            curveTo(813.5F, 840.9F, 840.9F, 813.5F, 840.9F, 780.6F)
            lineTo(840.9F, 243.4F)
            curveTo(840.9F, 210.5F, 813.5F, 183.1F, 780.6F, 183.1F)
            close()
        }.build()
        return _facebook!!
    }

private var _facebook: ImageVector? = null