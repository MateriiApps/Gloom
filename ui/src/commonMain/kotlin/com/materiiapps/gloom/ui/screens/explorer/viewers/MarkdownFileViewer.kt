package com.materiiapps.gloom.ui.screens.explorer.viewers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.materiiapps.gloom.gql.fragment.RepoFile
import com.materiiapps.gloom.ui.widgets.Markdown

@Composable
fun MarkdownFileViewer(
    markdownFile: RepoFile.OnMarkdownFileType
) {
    markdownFile.contentHTML?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Markdown(
                text = it,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}