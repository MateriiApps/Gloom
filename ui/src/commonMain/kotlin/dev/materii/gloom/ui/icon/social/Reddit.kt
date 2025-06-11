package dev.materii.gloom.ui.icon.social

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.materii.gloom.ui.icon.SocialIcons

val SocialIcons.Reddit: ImageVector
    get() {
        if (_reddit != null) {
            return _reddit!!
        }
        _reddit = ImageVector.Builder(
            name = "Reddit",
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
            moveTo(574.7F, 169.0F)
            curveTo(541.7F, 169.0F, 503.1F, 194.9F, 497.3F, 310.5F)
            curveTo(502.2F, 310.3F, 507.0F, 310.0F, 512.0F, 310.0F)
            curveTo(517.5F, 310.0F, 523.2F, 310.3F, 528.7F, 310.5F)
            curveTo(532.4F, 241.0F, 548.9F, 200.3F, 574.7F, 200.3F)
            curveTo(585.7F, 200.3F, 591.9F, 206.3F, 603.6F, 218.9F)
            curveTo(617.1F, 233.7F, 634.9F, 252.8F, 669.7F, 260.1F)
            curveTo(669.0F, 255.8F, 668.7F, 251.6F, 668.7F, 247.3F)
            curveTo(668.7F, 240.7F, 669.4F, 234.5F, 670.6F, 228.2F)
            curveTo(649.1F, 222.3F, 637.7F, 209.9F, 626.6F, 197.9F)
            curveTo(614.2F, 184.4F, 599.9F, 169.0F, 574.7F, 169.0F)

            moveTo(762.7F, 184.6F)
            curveTo(728.1F, 184.6F, 700.0F, 212.8F, 700.0F, 247.3F)
            curveTo(700.0F, 281.8F, 728.1F, 310.0F, 762.7F, 310.0F)
            curveTo(797.3F, 310.0F, 825.4F, 281.8F, 825.4F, 247.3F)
            curveTo(825.4F, 212.8F, 797.3F, 184.6F, 762.7F, 184.6F)

            moveTo(512.0F, 341.3F)
            curveTo(321.9F, 341.3F, 167.3F, 438.1F, 167.3F, 576.3F)
            curveTo(167.3F, 714.5F, 321.9F, 827.0F, 512.0F, 827.0F)
            curveTo(702.1F, 827.0F, 856.7F, 714.5F, 856.7F, 576.3F)
            curveTo(856.7F, 438.1F, 702.1F, 341.3F, 512.0F, 341.3F)

            moveTo(237.8F, 356.0F)
            curveTo(214.9F, 356.0F, 193.2F, 365.4F, 176.6F, 382.0F)
            curveTo(149.6F, 408.9F, 144.1F, 447.8F, 159.5F, 479.9F)
            curveTo(183.2F, 434.0F, 223.9F, 394.9F, 276.5F, 365.8F)
            curveTo(264.6F, 359.7F, 251.3F, 356.0F, 237.8F, 356.0F)

            moveTo(786.2F, 356.0F)
            curveTo(772.7F, 356.0F, 759.4F, 359.7F, 747.5F, 365.8F)
            curveTo(800.1F, 394.9F, 840.8F, 434.0F, 864.5F, 479.9F)
            curveTo(879.9F, 447.8F, 874.4F, 408.9F, 847.4F, 382.0F)
            curveTo(830.8F, 365.4F, 809.1F, 356.0F, 786.2F, 356.0F)
            lineTo(786.2F, 356.0F)

            moveTo(386.7F, 482.3F)
            curveTo(403.5F, 482.3F, 419.0F, 491.3F, 427.4F, 505.9F)
            curveTo(435.8F, 520.4F, 435.8F, 538.3F, 427.4F, 552.9F)
            curveTo(418.9F, 567.4F, 403.4F, 576.4F, 386.6F, 576.4F)
            curveTo(360.7F, 576.4F, 339.6F, 555.3F, 339.6F, 529.3F)
            curveTo(339.6F, 503.4F, 360.7F, 482.3F, 386.7F, 482.3F)
            lineTo(386.7F, 482.3F)

            moveTo(637.3F, 482.3F)
            curveTo(654.1F, 482.3F, 669.7F, 491.3F, 678.1F, 505.9F)
            curveTo(686.5F, 520.4F, 686.4F, 538.3F, 678.0F, 552.9F)
            curveTo(669.6F, 567.4F, 654.1F, 576.4F, 637.3F, 576.4F)
            curveTo(611.3F, 576.4F, 590.3F, 555.3F, 590.3F, 529.3F)
            curveTo(590.3F, 503.4F, 611.4F, 482.3F, 637.3F, 482.3F)
            lineTo(637.3F, 482.3F)

            moveTo(372.0F, 654.7F)
            curveTo(375.9F, 655.4F, 379.7F, 657.6F, 382.3F, 661.0F)
            curveTo(383.9F, 663.4F, 420.6F, 712.5F, 512.0F, 712.5F)
            curveTo(604.6F, 712.5F, 641.4F, 662.0F, 641.7F, 661.5F)
            curveTo(646.7F, 654.5F, 656.7F, 652.6F, 663.8F, 657.6F)
            curveTo(670.8F, 662.6F, 672.2F, 672.1F, 667.2F, 679.2F)
            curveTo(665.4F, 681.8F, 620.7F, 743.8F, 512.0F, 743.8F)
            curveTo(403.2F, 743.8F, 358.6F, 681.8F, 356.8F, 679.2F)
            curveTo(351.8F, 672.1F, 353.1F, 662.6F, 360.2F, 657.6F)
            curveTo(363.8F, 655.1F, 368.1F, 654.0F, 372.0F, 654.7F)
            lineTo(372.0F, 654.7F)
            close()
        }.build()
        return _reddit!!
    }

private var _reddit: ImageVector? = null