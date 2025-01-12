package dev.materii.gloom.ui.icon.custom

import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import dev.materii.gloom.ui.icon.CustomIcons

val CustomIcons.Fork: ImageVector
    get() {
        if (_fork != null) {
            return _fork!!
        }
        _fork = ImageVector.Builder(
            name = "Fork",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 1024.0F,
            viewportHeight = 1024.0F,
        ).materialPath {
            moveTo(823.9F, 199.7F)
            curveTo(823.9F, 142.0F, 777.6F, 95.7F, 719.9F, 95.7F)
            curveTo(662.3F, 95.7F, 616.0F, 142.0F, 616.0F, 199.7F)
            curveTo(616.0F, 237.9F, 637.1F, 272.0F, 668.0F, 289.8F)
            lineTo(668.0F, 375.9F)
            lineTo(512.0F, 548.1F)
            lineTo(356.0F, 375.9F)
            lineTo(356.0F, 289.8F)
            curveTo(386.9F, 272.0F, 408.0F, 238.7F, 408.0F, 199.7F)
            curveTo(408.0F, 142.0F, 361.7F, 95.7F, 304.1F, 95.7F)
            curveTo(246.4F, 95.7F, 200.1F, 142.0F, 200.1F, 199.7F)
            curveTo(200.1F, 237.9F, 221.2F, 272.0F, 252.1F, 289.8F)
            lineTo(252.1F, 416.6F)
            lineTo(460.0F, 645.6F)
            lineTo(460.0F, 734.2F)
            curveTo(429.1F, 752.0F, 408.0F, 785.3F, 408.0F, 824.3F)
            curveTo(408.0F, 882.0F, 454.3F, 928.3F, 512.0F, 928.3F)
            curveTo(569.7F, 928.3F, 616.0F, 882.0F, 616.0F, 824.3F)
            curveTo(616.0F, 786.1F, 594.8F, 752.0F, 564.0F, 734.2F)
            lineTo(564.0F, 645.6F)
            lineTo(771.9F, 416.6F)
            lineTo(771.9F, 289.8F)
            curveTo(802.8F, 272.0F, 823.9F, 238.7F, 823.9F, 199.7F)

            moveTo(304.1F, 148.5F)
            curveTo(331.7F, 148.5F, 354.4F, 171.3F, 354.4F, 198.9F)
            curveTo(354.4F, 226.5F, 331.7F, 249.2F, 304.1F, 249.2F)
            curveTo(276.4F, 249.2F, 253.7F, 226.5F, 253.7F, 198.9F)
            curveTo(253.7F, 171.3F, 276.4F, 148.5F, 304.1F, 148.5F)

            moveTo(512.0F, 872.2F)
            curveTo(484.4F, 872.2F, 461.6F, 849.5F, 461.6F, 821.9F)
            curveTo(461.6F, 794.3F, 484.4F, 771.5F, 512.0F, 771.5F)
            curveTo(539.6F, 771.5F, 562.4F, 794.3F, 562.4F, 821.9F)
            curveTo(562.4F, 849.5F, 539.6F, 872.2F, 512.0F, 872.2F)

            moveTo(719.9F, 148.5F)
            curveTo(747.6F, 148.5F, 770.3F, 171.3F, 770.3F, 198.9F)
            curveTo(770.3F, 226.5F, 747.6F, 249.2F, 719.9F, 249.2F)
            curveTo(692.3F, 249.2F, 669.6F, 226.5F, 669.6F, 198.9F)
            curveTo(669.6F, 171.3F, 692.3F, 148.5F, 719.9F, 148.5F)
            close()
        }.build()
        return _fork!!
    }

private var _fork: ImageVector? = null