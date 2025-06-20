package dev.materii.gloom.ui.widget.code

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.*
import dev.materii.gloom.ui.component.CodeText
import dev.materii.gloom.ui.component.scrollbar.ScrollBar
import dev.materii.gloom.ui.theme.CodeTheme
import dev.materii.gloom.ui.util.*
import dev.materii.gloom.ui.util.LazyUtil.getItemAtOffset
import dev.materii.gloom.util.toImmutableList

@Composable
fun CodeViewer(
    code: String,
    extension: String,
    modifier: Modifier = Modifier,
    linesSelected: IntRange? = null,
    onSelectLines: ((lineNumbers: IntRange?, snippet: String) -> Unit)? = null,
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
            contentPadding = PaddingValues(bottom = if (canScroll) DimenUtil.navBarPadding else 0.dp),
            modifier = Modifier
                .fillMaxSize()
                .then(modifier)
                .thenIf(onSelectLines != null) {
                    pointerInput(
                        verticalScrollState,
                        onSelectLines,
                        linesSelected
                    ) {
                        detectTapGestures(
                            onLongPress = { offset ->
                                // Only start a selection when there currently isn't one
                                if (linesSelected == null) {
                                    val itemPressed = verticalScrollState.getItemAtOffset(offset)
                                    val lineNumber = itemPressed?.index?.plus(1)
                                    lineNumber?.let {
                                        onSelectLines!!(
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
                                            lineNumber > linesSelected.last   -> linesSelected.first..lineNumber
                                            lineNumber < linesSelected.first  -> lineNumber..linesSelected.last
                                            lineNumber == linesSelected.last  -> linesSelected.first until linesSelected.last
                                            lineNumber == linesSelected.first -> linesSelected.first + 1..linesSelected.last
                                            else                              -> linesSelected
                                        }

                                        onSelectLines!!(
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
                                if (onSelectLines != null && onDoubleClick != null) {
                                    verticalScrollState
                                        .getItemAtOffset(offset)
                                        ?.let { itemPressed ->
                                            // Get the MutableInteractionState for the item
                                            interactionMap[itemPressed.index]?.let { interactionSource ->
                                                val press = PressInteraction.Press(
                                                    Offset(
                                                        x = offset.x, // Doesn't have to be changed
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
                        .thenIf(onSelectLines != null) {
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
                .padding(bottom = if (canScroll) DimenUtil.navBarPadding else 0.dp)
        )
    }
}