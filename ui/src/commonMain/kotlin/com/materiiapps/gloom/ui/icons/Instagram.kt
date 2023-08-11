package com.materiiapps.gloom.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val SocialIcons.Instagram: ImageVector
    get() {
        if (_instagram != null) {
            return _instagram!!
        }
        _instagram = ImageVector.Builder(
            name = "Instagram",
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
            moveTo(365.8F, 183.1F)
            curveTo(264.9F, 183.1F, 183.1F, 264.9F, 183.1F, 365.8F)
            lineTo(183.1F, 658.2F)
            curveTo(183.1F, 759.1F, 264.9F, 840.9F, 365.8F, 840.9F)
            lineTo(658.2F, 840.9F)
            curveTo(759.1F, 840.9F, 840.9F, 759.1F, 840.9F, 658.2F)
            lineTo(840.9F, 365.8F)
            curveTo(840.9F, 264.9F, 759.1F, 183.1F, 658.2F, 183.1F)
            lineTo(365.8F, 183.1F)

            moveTo(731.3F, 256.2F)
            curveTo(751.4F, 256.2F, 767.8F, 272.6F, 767.8F, 292.7F)
            curveTo(767.8F, 312.8F, 751.4F, 329.3F, 731.3F, 329.3F)
            curveTo(711.2F, 329.3F, 694.7F, 312.8F, 694.7F, 292.7F)
            curveTo(694.7F, 272.6F, 711.2F, 256.2F, 731.3F, 256.2F)

            moveTo(512.0F, 329.3F)
            curveTo(612.9F, 329.3F, 694.7F, 411.1F, 694.7F, 512.0F)
            curveTo(694.7F, 612.9F, 612.9F, 694.7F, 512.0F, 694.7F)
            curveTo(411.1F, 694.7F, 329.3F, 612.9F, 329.3F, 512.0F)
            curveTo(329.3F, 411.1F, 411.1F, 329.3F, 512.0F, 329.3F)

            moveTo(512.0F, 402.4F)
            curveTo(472.8F, 402.4F, 436.6F, 423.3F, 417.0F, 457.2F)
            curveTo(397.5F, 491.2F, 397.5F, 533.0F, 417.1F, 566.9F)
            curveTo(436.7F, 600.8F, 472.9F, 621.7F, 512.1F, 621.7F)
            curveTo(572.6F, 621.7F, 621.7F, 572.6F, 621.7F, 512.0F)
            curveTo(621.7F, 451.4F, 572.6F, 402.3F, 512.0F, 402.4F)
            lineTo(512.0F, 402.4F)
            close()
        }.build()
        return _instagram!!
    }

private var _instagram: ImageVector? = null