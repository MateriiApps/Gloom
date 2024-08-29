package com.materiiapps.gloom.ui.icon.social

import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.ui.icon.SocialIcons

val SocialIcons.GitHub: ImageVector
    get() {
        if (_gitHub != null) {
            return _gitHub!!
        }
        _gitHub = ImageVector.Builder(
            name = "GitHub",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 1024.0F,
            viewportHeight = 1024.0F,
        ).materialPath {
            moveTo(512.0F, 83.4F)
            curveTo(278.9F, 83.4F, 88.1F, 274.1F, 88.1F, 507.2F)
            curveTo(88.1F, 687.4F, 201.2F, 841.0F, 358.3F, 902.9F)
            curveTo(374.2F, 899.3F, 388.4F, 885.2F, 388.4F, 867.5F)
            lineTo(388.4F, 789.8F)
            lineTo(342.5F, 789.8F)
            curveTo(308.9F, 789.8F, 280.6F, 773.9F, 266.5F, 749.2F)
            curveTo(263.0F, 742.1F, 259.4F, 733.3F, 255.9F, 724.5F)
            curveTo(248.8F, 705.1F, 240.0F, 683.9F, 222.4F, 671.5F)
            curveTo(215.3F, 666.2F, 211.8F, 655.6F, 213.5F, 646.8F)
            curveTo(217.1F, 637.9F, 225.9F, 630.9F, 241.8F, 632.6F)
            curveTo(259.4F, 634.4F, 285.9F, 653.8F, 301.8F, 675.0F)
            curveTo(316.0F, 692.7F, 326.6F, 703.3F, 347.7F, 703.3F)
            lineTo(353.0F, 703.3F)
            curveTo(368.9F, 703.3F, 407.8F, 703.3F, 414.9F, 696.2F)
            curveTo(420.2F, 689.2F, 423.7F, 683.9F, 429.0F, 678.6F)
            curveTo(323.0F, 657.4F, 263.0F, 595.6F, 263.0F, 501.9F)
            curveTo(263.0F, 470.2F, 271.8F, 438.4F, 291.2F, 410.1F)
            curveTo(284.2F, 383.6F, 268.3F, 314.7F, 301.8F, 284.7F)
            lineTo(307.1F, 279.4F)
            lineTo(314.2F, 279.4F)
            curveTo(360.1F, 279.4F, 393.7F, 298.8F, 414.9F, 314.7F)
            curveTo(476.7F, 291.8F, 547.3F, 291.8F, 609.1F, 314.7F)
            curveTo(628.6F, 298.8F, 662.1F, 279.4F, 709.8F, 279.4F)
            lineTo(716.9F, 279.4F)
            lineTo(722.2F, 284.7F)
            curveTo(755.7F, 316.5F, 739.8F, 383.6F, 732.8F, 410.1F)
            curveTo(750.4F, 438.4F, 761.0F, 470.2F, 761.0F, 501.9F)
            curveTo(761.0F, 595.6F, 701.0F, 657.4F, 596.8F, 678.6F)
            curveTo(623.3F, 706.8F, 637.4F, 749.2F, 637.4F, 782.8F)
            lineTo(637.4F, 869.3F)
            curveTo(637.4F, 887.0F, 649.8F, 901.1F, 667.4F, 904.6F)
            curveTo(822.8F, 841.0F, 935.9F, 687.4F, 935.9F, 507.2F)
            curveTo(935.9F, 274.1F, 745.1F, 83.4F, 512.0F, 83.4F)
            close()
        }.build()
        return _gitHub!!
    }

private var _gitHub: ImageVector? = null