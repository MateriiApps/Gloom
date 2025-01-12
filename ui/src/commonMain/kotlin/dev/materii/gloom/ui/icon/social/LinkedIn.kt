package dev.materii.gloom.ui.icon.social

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.materii.gloom.ui.icon.SocialIcons

val SocialIcons.LinkedIn: ImageVector
    get() {
        if (_linkedIn != null) {
            return _linkedIn!!
        }
        _linkedIn = ImageVector.Builder(
            name = "LinkedIn",
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
            moveTo(840.9F, 749.6F)
            curveTo(840.9F, 800.0F, 800.0F, 840.9F, 749.6F, 840.9F)
            lineTo(274.4F, 840.9F)
            curveTo(224.0F, 840.9F, 183.1F, 800.0F, 183.1F, 749.6F)
            lineTo(183.1F, 274.4F)
            curveTo(183.1F, 224.0F, 224.0F, 183.1F, 274.4F, 183.1F)
            lineTo(749.6F, 183.1F)
            curveTo(800.0F, 183.1F, 840.9F, 224.0F, 840.9F, 274.4F)
            lineTo(840.9F, 749.6F)

            moveTo(292.7F, 420.6F)
            lineTo(384.1F, 420.6F)
            lineTo(384.1F, 731.3F)
            lineTo(292.7F, 731.3F)
            lineTo(292.7F, 420.6F)
            lineTo(292.7F, 420.6F)

            moveTo(338.1F, 384.1F)
            lineTo(337.6F, 384.1F)
            curveTo(310.4F, 384.1F, 292.7F, 363.8F, 292.7F, 338.4F)
            curveTo(292.7F, 312.5F, 310.9F, 292.7F, 338.7F, 292.7F)
            curveTo(366.5F, 292.7F, 383.6F, 312.5F, 384.1F, 338.4F)
            curveTo(384.1F, 363.8F, 366.5F, 384.1F, 338.1F, 384.1F)
            lineTo(338.1F, 384.1F)
            lineTo(338.1F, 384.1F)

            moveTo(731.3F, 731.3F)
            lineTo(639.9F, 731.3F)
            lineTo(639.9F, 565.0F)
            curveTo(639.9F, 524.8F, 617.5F, 497.4F, 581.6F, 497.4F)
            curveTo(554.2F, 497.4F, 539.3F, 515.9F, 532.1F, 533.8F)
            curveTo(529.5F, 540.2F, 530.3F, 557.9F, 530.3F, 566.8F)
            lineTo(530.3F, 731.3F)
            lineTo(438.9F, 731.3F)
            lineTo(438.9F, 420.6F)
            lineTo(530.3F, 420.6F)
            lineTo(530.3F, 468.4F)
            curveTo(543.4F, 448.0F, 564.1F, 420.6F, 616.9F, 420.6F)
            curveTo(682.2F, 420.6F, 731.3F, 461.7F, 731.3F, 553.6F)
            lineTo(731.3F, 731.3F)
            lineTo(731.3F, 731.3F)
            lineTo(731.3F, 731.3F)
            close()
        }.build()
        return _linkedIn!!
    }

private var _linkedIn: ImageVector? = null