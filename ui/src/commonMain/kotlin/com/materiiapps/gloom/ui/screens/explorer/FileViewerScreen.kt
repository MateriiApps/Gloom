package com.materiiapps.gloom.ui.screens.explorer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.materiiapps.gloom.gql.fragment.RepoFile
import com.materiiapps.gloom.ui.components.RefreshIndicator
import com.materiiapps.gloom.ui.components.toolbar.SmallToolbar
import com.materiiapps.gloom.ui.screens.explorer.viewers.MarkdownFileViewer
import com.materiiapps.gloom.ui.screens.explorer.viewers.TextFileViewer
import com.materiiapps.gloom.ui.viewmodels.explorer.FileViewerViewModel
import org.koin.core.parameter.parametersOf

class FileViewerScreen(
    private val owner: String,
    private val name: String,
    private val branch: String,
    private val path: String
): Screen {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    override fun Content() {
        val viewModel: FileViewerViewModel = getScreenModel { parametersOf(FileViewerViewModel.Input(owner, name, branch, path)) }
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val pullRefreshState = rememberPullRefreshState(viewModel.isLoading, onRefresh = { viewModel.getRepoFile() })
        val file = viewModel.file?.gitObject?.onCommit?.file

        Scaffold(
            topBar = { Toolbar(scrollBehavior, file) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { pv ->
            Box(
                modifier = Modifier
                    .padding(pv)
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {
                when(viewModel.hasError) {
                    true -> {
                        Icon(
                            Icons.Filled.Cancel,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    false -> FileContent(file)
                }

                RefreshIndicator(pullRefreshState, viewModel.isLoading)
            }
        }
    }

    @Composable
    private fun FileContent(file: RepoFile.File?) {
        when(file?.fileType?.__typename) {
            "MarkdownFileType" -> MarkdownFileViewer(file.fileType?.onMarkdownFileType!!)
            "ImageFileType" -> Box(Modifier.fillMaxSize())
            "PdfFileType" -> Box(Modifier.fillMaxSize())
            "TextFileType" -> TextFileViewer(file.fileType?.onTextFileType!!, file.extension ?: "")
            else -> {}
        }
    }

    @Composable
    private fun RowScope.FileActions(file: RepoFile.File?) {
        // TODO: Different actions for each file type
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Toolbar(
        scrollBehavior: TopAppBarScrollBehavior,
        file: RepoFile.File?
    ) {
        SmallToolbar(
            title = path.split("/").lastOrNull() ?: "File",
            actions = { FileActions(file) },
            scrollBehavior = scrollBehavior
        )
    }

}