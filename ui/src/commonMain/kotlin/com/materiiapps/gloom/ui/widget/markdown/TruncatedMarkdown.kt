package com.materiiapps.gloom.ui.widget.markdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Version of the [Markdown] component that limits
 * the height and applies a fade effect
 *
 * @param html The rendered HTML, returned from the GitHub API
 * @param modifier The [Modifier] used to style this [TruncatedMarkdown] component
 * @param truncatedHeight The max height for the markdown, if the markdown exceeds this
 * then a fade effect will be shown
 */
@Composable
fun TruncatedMarkdown(
    html: String,
    modifier: Modifier = Modifier,
    truncatedHeight: Dp = 300.dp
) {
    val density = LocalDensity.current
    var height by remember { mutableStateOf(0.dp) }

    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = modifier
    ) {
        Markdown(
            html = html,
            modifier = Modifier
                .heightIn(max = truncatedHeight)
                .onGloballyPositioned {
                    height = with(density) {
                        it.size.height.toDp()
                    }
                }
        )

        if (height >= truncatedHeight) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(300.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.surfaceContainerLow
                            )
                        )
                    )
            )
        }
    }
}