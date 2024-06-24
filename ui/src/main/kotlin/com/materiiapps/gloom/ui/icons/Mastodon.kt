package com.materiiapps.gloom.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val SocialIcons.Mastodon: ImageVector
    get() {
        if (_mastodon != null) {
            return _mastodon!!
        }
        _mastodon = ImageVector.Builder(
            name = "Mastodon",
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
            moveTo(839.1F, 391.6F)
            curveTo(839.1F, 239.4F, 739.4F, 194.9F, 739.4F, 194.9F)
            curveTo(689.1F, 171.8F, 602.8F, 162.1F, 513.2F, 161.3F)
            lineTo(511.0F, 161.3F)
            curveTo(421.3F, 162.1F, 335.1F, 171.8F, 284.8F, 194.9F)
            curveTo(284.8F, 194.9F, 185.1F, 239.5F, 185.1F, 391.6F)
            curveTo(185.1F, 426.4F, 184.4F, 468.1F, 185.5F, 512.3F)
            curveTo(189.1F, 661.0F, 212.8F, 807.7F, 350.4F, 844.1F)
            curveTo(413.8F, 860.9F, 468.3F, 864.4F, 512.1F, 862.0F)
            curveTo(591.6F, 857.6F, 636.3F, 833.6F, 636.3F, 833.6F)
            lineTo(633.6F, 775.9F)
            curveTo(633.6F, 775.9F, 576.8F, 793.8F, 513.0F, 791.6F)
            curveTo(449.7F, 789.4F, 383.0F, 784.8F, 372.7F, 707.2F)
            curveTo(371.8F, 699.9F, 371.3F, 692.7F, 371.3F, 685.4F)
            curveTo(371.3F, 685.4F, 433.4F, 700.6F, 512.1F, 704.2F)
            curveTo(560.2F, 706.4F, 605.3F, 701.4F, 651.1F, 695.9F)
            curveTo(739.0F, 685.4F, 815.5F, 631.3F, 825.1F, 581.8F)
            curveTo(840.3F, 503.8F, 839.1F, 391.6F, 839.1F, 391.6F)

            moveTo(721.5F, 587.6F)
            lineTo(648.5F, 587.6F)
            lineTo(648.5F, 408.8F)
            curveTo(648.5F, 371.1F, 632.7F, 352.0F, 600.9F, 352.0F)
            curveTo(565.9F, 352.0F, 548.3F, 374.7F, 548.3F, 419.6F)
            lineTo(548.3F, 517.5F)
            lineTo(475.8F, 517.5F)
            lineTo(475.8F, 419.6F)
            curveTo(475.8F, 374.7F, 458.2F, 352.0F, 423.1F, 352.0F)
            curveTo(391.3F, 352.0F, 375.5F, 371.1F, 375.5F, 408.8F)
            lineTo(375.5F, 587.6F)
            lineTo(302.5F, 587.6F)
            lineTo(302.5F, 403.4F)
            curveTo(302.5F, 365.7F, 312.1F, 335.8F, 331.4F, 313.7F)
            curveTo(351.2F, 291.6F, 377.2F, 280.2F, 409.5F, 280.2F)
            curveTo(446.9F, 280.2F, 475.2F, 294.6F, 493.9F, 323.3F)
            lineTo(512.0F, 353.8F)
            lineTo(530.2F, 323.3F)
            curveTo(548.9F, 294.6F, 577.2F, 280.2F, 614.5F, 280.2F)
            curveTo(646.8F, 280.2F, 672.8F, 291.6F, 692.7F, 313.7F)
            curveTo(711.9F, 335.8F, 721.5F, 365.7F, 721.5F, 403.4F)
            lineTo(721.5F, 587.6F)
            close()
        }.build()
        return _mastodon!!
    }

private var _mastodon: ImageVector? = null