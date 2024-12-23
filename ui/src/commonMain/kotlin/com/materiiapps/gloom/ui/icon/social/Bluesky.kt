package com.materiiapps.gloom.ui.icon.social

import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.ui.icon.SocialIcons

val SocialIcons.Bluesky: ImageVector
    get() {
        if (_bluesky != null) {
            return _bluesky!!
        }
        _bluesky = ImageVector.Builder(
            name = "Bluesky",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 1024.0F,
            viewportHeight = 1024.0F,
        ).materialPath {
            moveTo(300.3F, 227.2F)
            curveTo(386.0F, 291.5F, 478.2F, 422.0F, 512.0F, 492.0F)
            curveTo(545.9F, 422.0F, 638.0F, 291.5F, 723.7F, 227.2F)
            curveTo(785.6F, 180.8F, 885.8F, 144.8F, 885.8F, 259.2F)
            curveTo(885.8F, 282.0F, 872.7F, 450.9F, 865.0F, 478.4F)
            curveTo(838.3F, 573.7F, 741.1F, 598.0F, 654.6F, 583.3F)
            curveTo(805.8F, 609.1F, 844.3F, 694.3F, 761.2F, 779.5F)
            curveTo(603.4F, 941.4F, 534.5F, 738.9F, 516.8F, 687.0F)
            curveTo(513.5F, 677.5F, 512.0F, 673.1F, 512.0F, 676.8F)
            curveTo(512.0F, 673.1F, 510.5F, 677.5F, 507.2F, 687.0F)
            curveTo(489.5F, 738.9F, 420.6F, 941.4F, 262.8F, 779.5F)
            curveTo(179.7F, 694.3F, 218.2F, 609.0F, 369.4F, 583.3F)
            curveTo(282.9F, 598.0F, 185.7F, 573.7F, 159.0F, 478.4F)
            curveTo(151.3F, 450.9F, 138.2F, 282.0F, 138.2F, 259.2F)
            curveTo(138.2F, 144.8F, 238.4F, 180.8F, 300.3F, 227.2F)
            lineTo(300.3F, 227.2F)
            close()
        }.build()
        return _bluesky!!
    }

private var _bluesky: ImageVector? = null