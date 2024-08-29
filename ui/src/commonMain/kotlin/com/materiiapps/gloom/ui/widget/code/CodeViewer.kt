package com.materiiapps.gloom.ui.widget.code

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materiiapps.gloom.ui.component.CodeText
import com.materiiapps.gloom.ui.component.scrollbar.ScrollBar
import com.materiiapps.gloom.ui.theme.CodeTheme
import com.materiiapps.gloom.ui.util.DimenUtils
import com.materiiapps.gloom.ui.util.LazyUtil.getItemAtOffset
import com.materiiapps.gloom.ui.util.digits
import com.materiiapps.gloom.ui.util.padLineNumber
import com.materiiapps.gloom.ui.util.thenIf
import com.materiiapps.gloom.ui.util.toDp
import com.materiiapps.gloom.ui.util.toImmutableList

@Composable
fun CodeViewer(
    code: String,
    extension: String,
    modifier: Modifier = Modifier,
    linesSelected: IntRange? = null,
    onLinesSelected: ((lineNumbers: IntRange?, snippet: String) -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    verticalScrollState: LazyListState = rememberLazyListState(),
    horizontalScrollState: ScrollState = rememberScrollState(),
    theme: CodeTheme = CodeTheme.getDefault(),
    codePadding: Dp = 16.dp,
    fontSize: TextUnit = 13.sp,
    displayLineNumber: Boolean = true,
    lineNumberPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 3.dp)
) {
    // Layout
    var maxLineNumWidth by remember { mutableIntStateOf(0) }
    val maxLineNumWidthAnimated by animateIntAsState(maxLineNumWidth, label = "Line number width")
    val layoutDirection = LocalLayoutDirection.current
    val lineNumberHorizontalPadding = remember(layoutDirection) {
        lineNumberPadding.calculateStartPadding(layoutDirection) + lineNumberPadding.calculateEndPadding(
            layoutDirection
        )
    }

    // Scrolling
    val canScroll = verticalScrollState.canScrollForward || verticalScrollState.canScrollBackward

    // Line processing
    val lines = remember(code) {
        code.split("\n").toImmutableList()
    }
    val maxLineLength = remember(lines) {
        lines.maxOf { it.length }
    }

    // Line click handling
    val interactionMap = remember(lines.size) {
        mutableStateMapOf<Int, MutableInteractionSource>()
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(
            state = verticalScrollState,
            contentPadding = PaddingValues(bottom = if (canScroll) DimenUtils.navBarPadding else 0.dp),
            modifier = Modifier
                .fillMaxSize()
                .then(modifier)
                .thenIf(onLinesSelected != null) {
                    pointerInput(
                        verticalScrollState,
                        onLinesSelected,
                        linesSelected
                    ) {
                        detectTapGestures(
                            onLongPress = { offset ->
                                // Only start a selection when there currently isn't one
                                if (linesSelected == null) {
                                    val itemPressed = verticalScrollState.getItemAtOffset(offset)
                                    val lineNumber = itemPressed?.index?.plus(1)
                                    lineNumber?.let {
                                        onLinesSelected!!(
                                            lineNumber..lineNumber,
                                            lines[lineNumber - 1].trimIndent()
                                        )
                                    }
                                }
                            },
                            onTap = { offset ->
                                // Only handle when at least one other line is selected, otherwise call onClick
                                if (linesSelected != null) {
                                    val itemPressed = verticalScrollState.getItemAtOffset(offset)
                                    val lineNumber = itemPressed?.index?.plus(1)
                                    lineNumber?.let {
                                        val newRange = when {
                                            lineNumber > linesSelected.last -> linesSelected.first..lineNumber
                                            lineNumber < linesSelected.first -> lineNumber..linesSelected.last
                                            lineNumber == linesSelected.last -> linesSelected.first until linesSelected.last
                                            lineNumber == linesSelected.first -> linesSelected.first + 1..linesSelected.last
                                            else -> linesSelected
                                        }

                                        onLinesSelected!!(
                                            if (newRange.isEmpty()) null else newRange,
                                            lines
                                                .subList(newRange.first - 1, newRange.last)
                                                .joinToString("\n")
                                                .trimIndent()
                                        )
                                    }
                                }
                            },
                            onDoubleTap = {
                                onDoubleClick?.invoke()
                            },
                            onPress = { offset ->
                                // Check if any press related listeners are present
                                if (onLinesSelected != null && onDoubleClick != null) {
                                    verticalScrollState
                                        .getItemAtOffset(offset)
                                        ?.let { itemPressed ->
                                            // Get the MutableInteractionState for the item
                                            interactionMap[itemPressed.index]?.let { interactionSource ->
                                                val press = PressInteraction.Press(
                                                    Offset(
                                                        x = offset.x, // Since both the lazy column and line are the same width we don't have to change this
                                                        y = itemPressed.size / 2f // Center the interaction
                                                    )
                                                )
                                                interactionSource.emit(press)
                                                if (tryAwaitRelease()) {
                                                    interactionSource.emit(
                                                        PressInteraction.Release(
                                                            press
                                                        )
                                                    )
                                                } else {
                                                    interactionSource.emit(
                                                        PressInteraction.Cancel(
                                                            press
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                }
                            }
                        )
                    }
                }
                .horizontalScroll(horizontalScrollState)
        ) {
            itemsIndexed(
                items = lines,
                key = { i, code -> "$i-${code.sumOf { it.code }}" }
            ) { i, code ->
                val interactionSource =
                    remember { MutableInteractionSource().also { interactionMap[i] = it } }

                @Suppress("DEPRECATION_ERROR") // Necessary for rememberRipple bc compose devs are stupid
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .background(theme.background)
                        .indication(interactionSource, rememberRipple())
                        .thenIf(onLinesSelected != null) {
                            semantics {
                                role = Role.Checkbox
                                if (linesSelected != null) {
                                    selected = i + 1 in linesSelected
                                }
                            }
                        }
                        .thenIf(linesSelected != null && i + 1 in linesSelected) {
                            drawWithContent {
                                drawContent()
                                drawRect(
                                    color = theme.selectedHighlight.copy(alpha = 0.2f),
                                    size = drawContext.size
                                )
                            }
                        }
                ) {
                    // Highlighted code
                    CodeText(
                        text = code.padEnd(maxLineLength) /* Probably a better way to do this but idk */,
                        extension = extension,
                        theme = theme.syntaxTheme,
                        fontSize = fontSize,
                        lineHeight = fontSize,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(
                                start = maxLineNumWidthAnimated.toDp()
                                        + lineNumberHorizontalPadding /* Account for line number padding */
                                        + codePadding, /* Desired padding */
                                end = codePadding
                            )
                    )

                    // Line number
                    if (displayLineNumber) {
                        Text(
                            (i + 1).padLineNumber(maxDigits = lines.size.digits),
                            fontFamily = FontFamily.Monospace,
                            fontSize = fontSize,
                            textAlign = TextAlign.End,
                            color = theme.linesContent,
                            fontWeight = FontWeight.Bold,
                            lineHeight = fontSize,
                            modifier = Modifier
                                .offset { IntOffset(horizontalScrollState.value, 0) }
                                .background(theme.linesBackground)
                                .padding(lineNumberPadding)
                                .onGloballyPositioned {
                                    if (it.size.width != maxLineNumWidth)
                                        maxLineNumWidth = it.size.width
                                }
                        )
                    }
                }
            }
        }

        ScrollBar(
            scrollState = horizontalScrollState,
            orientation = Orientation.Horizontal,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = if (canScroll) DimenUtils.navBarPadding else 0.dp)
        )
    }
}