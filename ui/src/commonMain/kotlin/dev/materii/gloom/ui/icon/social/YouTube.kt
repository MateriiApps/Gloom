package dev.materii.gloom.ui.icon.social

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.materii.gloom.ui.icon.SocialIcons

val SocialIcons.YouTube: ImageVector
    get() {
        if (_youTube != null) {
            return _youTube!!
        }
        _youTube = ImageVector.Builder(
            name = "YouTube",
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
            pathFillType = PathFillType.EvenOdd,
        ) {
            moveTo(902.5F, 713.8F)
            curveTo(894.3F, 756.6F, 859.9F, 789.4F, 816.8F, 795.4F)
            curveTo(749.6F, 805.6F, 637.4F, 817.9F, 511.0F, 817.9F)
            curveTo(408.6F, 817.6F, 306.4F, 810.1F, 205.1F, 795.4F)
            curveTo(162.0F, 789.4F, 127.6F, 756.6F, 119.4F, 713.8F)
            curveTo(111.2F, 667.0F, 103.1F, 597.6F, 103.1F, 512.0F)
            curveTo(103.1F, 426.4F, 111.2F, 357.0F, 119.4F, 310.2F)
            curveTo(127.6F, 267.4F, 162.0F, 234.6F, 205.1F, 228.6F)
            curveTo(272.4F, 218.4F, 384.5F, 206.1F, 511.0F, 206.1F)
            curveTo(637.4F, 206.1F, 747.6F, 218.4F, 816.8F, 228.6F)
            curveTo(859.9F, 234.6F, 894.3F, 267.4F, 902.5F, 310.2F)
            curveTo(910.7F, 357.0F, 920.9F, 426.4F, 920.9F, 512.0F)
            curveTo(918.8F, 597.6F, 910.7F, 667.0F, 902.5F, 713.8F)
            lineTo(902.5F, 713.8F)

            moveTo(429.4F, 654.7F)
            lineTo(429.4F, 369.3F)
            lineTo(674.1F, 512.0F)
            lineTo(429.4F, 654.7F)
            close()
        }.build()
        return _youTube!!
    }

private var _youTube: ImageVector? = null