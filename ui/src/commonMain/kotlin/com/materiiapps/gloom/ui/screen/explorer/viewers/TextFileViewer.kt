package com.materiiapps.gloom.ui.screen.explorer.viewers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.utils.DimenUtils.multiply
import com.materiiapps.gloom.ui.widgets.code.CodeViewer
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun TextFileViewer(
    content: String,
    extension: String,
    linesSelected: IntRange?,
    onHideToggled: () -> Unit,
    onLinesSelected: (lineNumbers: IntRange?, snippet: String) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current
    val defaultLineNumberPadding = PaddingValues(horizontal = 8.dp, vertical = 3.dp)

    var scale by remember { mutableFloatStateOf(1f) }
    var lineNumberPadding by remember { mutableStateOf(defaultLineNumberPadding) }
    var hideFAB by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        CodeViewer(
            code = content,
            extension = extension,
            fontSize = 13.sp * scale,
            codePadding = 16.dp * scale,
            lineNumberPadding = lineNumberPadding,
            linesSelected = linesSelected,
            onLinesSelected = onLinesSelected,
            onDoubleClick = { // Double tap to hide the ui
                onHideToggled()
                hideFAB = !hideFAB
            },
            modifier = Modifier
                .pointerInput(Unit) {
                    // Pinch to zoom
                    detectTransformGestures { _, pan, zoom, _ ->
                        if (scale * zoom in 0.7f..2f) {
                            scale *= zoom
                            lineNumberPadding = lineNumberPadding.multiply(zoom, layoutDirection)
                        }
                        scrollState.dispatchRawDelta(-(pan.x.toDp().toPx()))
                        lazyListState.dispatchRawDelta(-(pan.y.toDp().toPx()))
                    }
                }
        )

        AnimatedVisibility(
            visible = scale != 1f && !hideFAB,
            enter = scaleIn(),
            exit = scaleOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .systemBarsPadding()
                .padding(16.dp)
        ) {
            LargeFloatingActionButton(
                shape = RoundedCornerShape(25),
                onClick = {
                    scale = 1f
                    lineNumberPadding = defaultLineNumberPadding
                },
                modifier = Modifier
                    .size(56.dp)
            ) {
                Icon(
                    Icons.Outlined.SearchOff,
                    contentDescription = stringResource(Res.strings.action_reset_zoom)
                )
            }
        }
    }
}