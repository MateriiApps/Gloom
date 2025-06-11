package dev.materii.gloom.ui.icon.social

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.materii.gloom.ui.icon.SocialIcons

val SocialIcons.Twitch: ImageVector
    get() {
        if (_twitch != null) {
            return _twitch!!
        }
        _twitch = ImageVector.Builder(
            name = "Twitch",
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
            moveTo(772.9F, 574.5F)
            lineTo(685.9F, 661.4F)
            lineTo(512.0F, 661.4F)
            lineTo(425.0F, 748.4F)
            lineTo(425.0F, 661.4F)
            lineTo(309.1F, 661.4F)
            lineTo(309.1F, 255.6F)
            lineTo(772.9F, 255.5F)
            lineTo(772.9F, 574.5F)
            lineTo(772.9F, 574.5F)

            moveTo(226.6F, 183.1F)
            lineTo(178.6F, 310.9F)
            lineTo(178.6F, 791.9F)
            lineTo(338.0F, 791.9F)
            lineTo(338.0F, 878.9F)
            lineTo(445.5F, 878.9F)
            lineTo(532.5F, 791.9F)
            lineTo(662.9F, 791.9F)
            lineTo(845.4F, 609.5F)
            lineTo(845.4F, 183.1F)
            lineTo(226.6F, 183.1F)
            lineTo(226.6F, 183.1F)

            moveTo(671.4F, 559.9F)
            lineTo(584.5F, 559.9F)
            lineTo(584.5F, 357.0F)
            lineTo(671.4F, 357.0F)
            lineTo(671.4F, 559.9F)
            lineTo(671.4F, 559.9F)

            moveTo(526.5F, 559.9F)
            lineTo(439.5F, 559.9F)
            lineTo(439.5F, 357.0F)
            lineTo(526.5F, 357.0F)
            lineTo(526.5F, 559.9F)
            lineTo(526.5F, 559.9F)
            close()
        }.build()
        return _twitch!!
    }

private var _twitch: ImageVector? = null
