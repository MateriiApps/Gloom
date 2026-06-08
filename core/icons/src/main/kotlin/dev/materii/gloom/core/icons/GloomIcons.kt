package dev.materii.gloom.core.icons

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.unit.dp
import dev.materii.gloom.core.icons.social.SocialIcons

object GloomIcons {

    val Social = SocialIcons

}

private const val GloomIconWidth = 24f
private const val GloomIconHeight = 24f

private const val GloomStrokeWidth = 1f

private val GloomStrokeLineCap = StrokeCap.Round
private val GloomStrokeLineJoin = StrokeJoin.Round
private val GloomStrokeColor = SolidColor(Color.Black)

/**
 * ImageVector preset for all Gloom icons
 */
internal fun gloomIcon(
    name: String,
    autoMirror: Boolean = false,
    builder: ImageVector.Builder.() -> Unit
) = ImageVector.Builder(
    name = name,
    defaultWidth = GloomIconWidth.dp,
    defaultHeight = GloomIconHeight.dp,
    viewportWidth = GloomIconWidth,
    viewportHeight = GloomIconHeight,
    autoMirror = autoMirror
).apply(builder).build()

/**
 * Path preset for use in Gloom icons
 */
internal fun ImageVector.Builder.gloomPath(
    name: String = DefaultPathName,
    fill: Brush? = null,
    fillAlpha: Float = 1.0f,
    stroke: Brush? = GloomStrokeColor,
    strokeAlpha: Float = 1.0f,
    strokeLineWidth: Float = GloomStrokeWidth,
    strokeLineCap: StrokeCap = GloomStrokeLineCap,
    strokeLineJoin: StrokeJoin = GloomStrokeLineJoin,
    strokeLineMiter: Float = 0f,
    pathFillType: PathFillType = DefaultFillType,
    pathBuilder: PathBuilder.() -> Unit
): ImageVector.Builder =
    path(
        name = name,
        fill = fill,
        fillAlpha = fillAlpha,
        stroke = stroke,
        strokeAlpha = strokeAlpha,
        strokeLineWidth = strokeLineWidth,
        strokeLineCap = strokeLineCap,
        strokeLineJoin = strokeLineJoin,
        strokeLineMiter = strokeLineMiter,
        pathFillType = pathFillType,
        pathBuilder = pathBuilder
    )