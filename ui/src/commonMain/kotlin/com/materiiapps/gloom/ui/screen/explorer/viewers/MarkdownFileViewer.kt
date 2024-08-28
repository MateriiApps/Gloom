package com.materiiapps.gloom.ui.screen.explorer.viewers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.gql.fragment.RawMarkdownFile
import com.materiiapps.gloom.gql.fragment.RepoFile
import com.materiiapps.gloom.ui.components.ErrorMessage
import com.materiiapps.gloom.ui.widgets.Markdown
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun MarkdownFileViewer(
    markdownFile: RepoFile.OnMarkdownFileType,
    rawFile: RawMarkdownFile? = null,
    showRaw: Boolean = false,
    rawHasError: Boolean = false,
    linesSelected: IntRange? = null,
    onHideToggled: () -> Unit = {},
    onLinesSelected: (lineNumbers: IntRange?, snippet: String) -> Unit = { _, _ -> }
) {
    when {
        showRaw -> {
            if (rawHasError) {
                ErrorMessage(
                    message = stringResource(Res.strings.msg_raw_markdown_fail)
                )
            } else {
                rawFile?.contentRaw?.let { raw ->
                    TextFileViewer(
                        content = raw,
                        extension = "md",
                        linesSelected = linesSelected,
                        onHideToggled = onHideToggled,
                        onLinesSelected = onLinesSelected
                    )
                }
            }
        }

        else -> {
            markdownFile.contentHTML?.let {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Markdown(
                        text = it,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}