package com.materiiapps.gloom.ui.screens.explorer.viewers

import androidx.compose.runtime.Composable
import com.materiiapps.gloom.gql.fragment.RepoFile
import com.materiiapps.gloom.ui.widgets.code.CodeViewer

@Composable
fun TextFileViewer(
    textFile: RepoFile.OnTextFileType,
    extension: String,
) {
    val content = textFile.contentRaw ?: return

    CodeViewer(
        code = content,
        extension = extension
    )
}