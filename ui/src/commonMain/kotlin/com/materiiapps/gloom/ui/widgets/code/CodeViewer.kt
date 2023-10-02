package com.materiiapps.gloom.ui.widgets.code

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materiiapps.gloom.ui.components.CodeText
import com.materiiapps.gloom.ui.theme.CodeTheme
import com.materiiapps.gloom.ui.utils.DimenUtils
import com.materiiapps.gloom.ui.utils.toImmutableList

@Composable
fun CodeViewer(
    code: String,
    extension: String,
    theme: CodeTheme = CodeTheme.getDefault(),
) {
    val lazyListState = rememberLazyListState()
    val scrollState = rememberScrollState()
    val canScroll = lazyListState.canScrollForward || lazyListState.canScrollBackward
    val lines = remember(code) {
        code.split("\n").toImmutableList()
    }

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(bottom = if(canScroll) DimenUtils.navBarPadding else 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .background(theme.background)
    ) {
        itemsIndexed(lines) { i, code ->
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                CodeText(
                    text = code,
                    extension = extension,
                    theme = theme.syntaxTheme,
                    modifier = Modifier
                        .padding(start = (38 + 16).dp, end = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .offset { IntOffset(scrollState.value, 0) }
                        .background(theme.linesBackground)
                ) {
                    Text(
                        (i + 1).toString(),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        textAlign = TextAlign.End,
                        color = theme.linesContent,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                            .width(24.dp)
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
}