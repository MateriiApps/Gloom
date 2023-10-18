package com.materiiapps.gloom.ui.widgets.code

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materiiapps.gloom.ui.components.CodeText
import com.materiiapps.gloom.ui.components.scrollbar.ScrollBar
import com.materiiapps.gloom.ui.theme.CodeTheme
import com.materiiapps.gloom.ui.utils.DimenUtils
import com.materiiapps.gloom.ui.utils.digits
import com.materiiapps.gloom.ui.utils.padLineNumber
import com.materiiapps.gloom.ui.utils.toDp
import com.materiiapps.gloom.ui.utils.toImmutableList

@Composable
fun CodeViewer(
    code: String,
    extension: String,
    theme: CodeTheme = CodeTheme.getDefault(),
    codePadding: Dp = 16.dp,
    lineNumberPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 3.dp)
) {
    // Layout
    var maxLineNumWidth by remember { mutableIntStateOf(0) }
    val maxLineNumWidthAnimated by animateIntAsState(maxLineNumWidth, label = "Line number width")
    val layoutDirection = LocalLayoutDirection.current
    val lineNumberHorizontalPadding = remember(lineNumberPadding, layoutDirection) {
        lineNumberPadding.calculateStartPadding(layoutDirection) + lineNumberPadding.calculateEndPadding(
            layoutDirection
        )
    }

    // State
    val lazyListState = rememberLazyListState()
    val scrollState = rememberScrollState()
    val canScroll = lazyListState.canScrollForward || lazyListState.canScrollBackward

    // Line processing
    val lines = remember(code) {
        code.split("\n").toImmutableList()
    }
    val maxLineLength = remember(lines) {
        lines.maxOf { it.length }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(bottom = if (canScroll) DimenUtils.navBarPadding else 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
        ) {
            itemsIndexed(
                items = lines,
                key = { i, code -> "$i-${code.sumOf { it.code }}" }
            ) { i, code ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.background(theme.background)
                ) {
                    CodeText(
                        text = code.padEnd(maxLineLength) /* Probably a better way to do this but idk */,
                        extension = extension,
                        theme = theme.syntaxTheme,
                        modifier = Modifier
                            .padding(
                                start = maxLineNumWidthAnimated.toDp()
                                        + lineNumberHorizontalPadding /* Account for line number padding */
                                        + codePadding, /* Desired padding */
                                end = codePadding
                            )
                            .fillParentMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .offset { IntOffset(scrollState.value, 0) }
                            .background(theme.linesBackground)
                    ) {
                        Text(
                            (i + 1).padLineNumber(maxDigits = lines.size.digits),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            textAlign = TextAlign.End,
                            color = theme.linesContent,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(lineNumberPadding)
                                .onGloballyPositioned {
                                    if (it.size.width > maxLineNumWidth) maxLineNumWidth =
                                        it.size.width
                                }
                        )
                        Box(
                            modifier = Modifier
                                .background(theme.linesContent)
                                .width(2.dp)
                                .fillMaxHeight()
                        )
                    }
                }
            }
        }

        ScrollBar(
            scrollState = scrollState,
            orientation = Orientation.Horizontal,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = if (canScroll) DimenUtils.navBarPadding else 0.dp)
        )
    }
}