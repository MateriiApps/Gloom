package com.materiiapps.gloom.ui.icon.social

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.ui.icon.SocialIcons

val SocialIcons.Twitter: ImageVector
    get() {
        if (_twitter != null) {
            return _twitter!!
        }
        _twitter = ImageVector.Builder(
            name = "Twitter",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 1024.0F,
            viewportHeight = 1024.0F,
        ).path(
            fill = SolidColor(Color(0xFFFFFFFF)),
            fillAlpha = 1.0F,
            strokeAlpha = 1.0F,
            strokeLineWidth = 0.0F,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 4.0F,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(917.0F, 270.2F)
            curveTo(887.8F, 283.1F, 856.8F, 291.6F, 825.1F, 295.3F)
            curveTo(858.5F, 275.3F, 883.5F, 243.8F, 895.4F, 206.8F)
            curveTo(864.0F, 225.4F, 829.7F, 238.5F, 793.8F, 245.7F)
            curveTo(748.9F, 197.7F, 679.3F, 182.0F, 618.1F, 206.1F)
            curveTo(557.0F, 230.3F, 516.9F, 289.4F, 516.9F, 355.1F)
            curveTo(516.9F, 367.7F, 518.3F, 379.9F, 521.1F, 391.5F)
            curveTo(392.4F, 385.2F, 272.4F, 324.4F, 191.2F, 224.3F)
            curveTo(176.9F, 248.7F, 169.4F, 276.5F, 169.5F, 304.8F)
            curveTo(169.5F, 360.3F, 197.7F, 409.3F, 240.7F, 438.0F)
            curveTo(215.3F, 437.2F, 190.4F, 430.3F, 168.2F, 417.9F)
            lineTo(168.2F, 420.0F)
            curveTo(168.2F, 496.2F, 221.9F, 561.9F, 296.6F, 576.9F)
            curveTo(273.1F, 583.4F, 248.3F, 584.3F, 224.3F, 579.7F)
            curveTo(245.3F, 644.9F, 305.3F, 689.6F, 373.8F, 690.9F)
            curveTo(306.7F, 743.6F, 221.5F, 767.4F, 136.9F, 757.1F)
            curveTo(210.0F, 804.1F, 295.2F, 829.1F, 382.2F, 829.1F)
            curveTo(676.6F, 829.1F, 837.7F, 585.2F, 837.7F, 373.6F)
            curveTo(837.7F, 366.8F, 837.4F, 359.8F, 837.1F, 352.9F)
            curveTo(868.4F, 330.3F, 895.5F, 302.3F, 917.0F, 270.2F)
            lineTo(917.0F, 270.2F)
            close()
        }.build()
        return _twitter!!
    }

private var _twitter: ImageVector? = null