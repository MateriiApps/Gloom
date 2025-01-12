package dev.materii.gloom.ui.util

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.LayoutDirection

/**
 * Default squircle shape
 */
val Squircle = Squircle(0.50f)

/**
 * Shape that's in-between a circle and a square.
 *
 * ```
 * MIT License
 *
 * Copyright (c) 2023 Stoyan Vuchev
 * ```
 *
 * @param radius Corner radius of the squircle (% of width, ex. 20% = 0.2f)
 */
@Suppress("FunctionName")
fun Squircle(
    radius: Float
) = Squircle(
    radius,
    radius,
    radius,
    radius
)

/**
 * Shape that's in-between a circle and a square.
 *
 * Corner radii should not exceed 0.5f
 *
 * Most of this code taken from [stoyan-vuchev/squircle-shape](https://github.com/stoyan-vuchev/squircle-shape)
 *
 * ```
 * MIT License
 *
 * Copyright (c) 2023 Stoyan Vuchev
 * ```
 *
 * @param topLeft Top left corner radius (% of width, ex. 20% = 0.2f)
 * @param topRight Top right corner radius (% of width, ex. 20% = 0.2f)
 * @param bottomLeft Bottom left corner radius (% of width, ex. 20% = 0.2f)
 * @param bottomRight Bottom left corner radius (% of width, ex. 20% = 0.2f)
 * @param cornerSmoothing How smooth to make the corners
 */
@Suppress("FunctionName")
fun Squircle(
    topLeft: Float,
    topRight: Float,
    bottomLeft: Float,
    bottomRight: Float,
    cornerSmoothing: Float = 0.70f
) = GenericShape(
    builder = { size: Size, _: LayoutDirection ->
        if (listOf(topLeft, topRight, bottomLeft, bottomRight).any { it > 0.5f }) throw IllegalArgumentException("No corner radius should exceed 50% (0.5f)")

        val width = size.width
        val height = size.height

        val topLeftCorner = width * topLeft
        val topRightCorner = width * topRight
        val bottomLeftCorner = width * bottomLeft
        val bottomRightCorner = width * bottomRight

        moveTo(
            x = topLeftCorner,
            y = 0f
        )

        lineTo(
            x = width - topRightCorner,
            y = 0f
        )

        cubicTo(
            x1 = width - topRightCorner * (1 - cornerSmoothing),
            y1 = 0f,
            x2 = width,
            y2 = topRightCorner * (1 - cornerSmoothing),
            x3 = width,
            y3 = topRightCorner
        )

        lineTo(
            x = width,
            y = height - bottomRightCorner
        )

        cubicTo(
            x1 = width,
            y1 = height - bottomRightCorner * (1 - cornerSmoothing),
            x2 = width - bottomRightCorner * (1 - cornerSmoothing),
            y2 = height,
            x3 = width - bottomRightCorner,
            y3 = height
        )

        lineTo(
            x = bottomLeftCorner,
            y = height
        )

        cubicTo(
            x1 = bottomLeftCorner * (1 - cornerSmoothing),
            y1 = height,
            x2 = 0f,
            y2 = height - bottomLeftCorner * (1 - cornerSmoothing),
            x3 = 0f,
            y3 = height - bottomLeftCorner
        )

        lineTo(
            x = 0f,
            y = topLeftCorner
        )

        cubicTo(
            x1 = 0f,
            y1 = topLeftCorner * (1 - cornerSmoothing),
            x2 = topLeftCorner * (1 - cornerSmoothing),
            y2 = 0f,
            x3 = topLeftCorner,
            y3 = 0f
        )

        close()
    }
)