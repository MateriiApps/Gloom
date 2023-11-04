package com.materiiapps.gloom.ui.screens.explorer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.domain.manager.ShareManager
import com.materiiapps.gloom.gql.fragment.RepoFile
import com.materiiapps.gloom.ui.components.ErrorMessage
import com.materiiapps.gloom.ui.components.RefreshIndicator
import com.materiiapps.gloom.ui.components.toolbar.SmallToolbar
import com.materiiapps.gloom.ui.screens.explorer.viewers.ImageFileViewer
import com.materiiapps.gloom.ui.screens.explorer.viewers.MarkdownFileViewer
import com.materiiapps.gloom.ui.screens.explorer.viewers.PdfFileViewer
import com.materiiapps.gloom.ui.screens.explorer.viewers.TextFileViewer
import com.materiiapps.gloom.ui.viewmodels.explorer.FileViewerViewModel
import dev.icerock.moko.resources.compose.stringResource
import org.koin.androidx.compose.get
import org.koin.core.parameter.parametersOf

class FileViewerScreen(
    private val owner: String,
    private val name: String,
    private val branch: String,
    private val path: String
) : Screen {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    override fun Content() {
        val viewModel: FileViewerViewModel =
            getScreenModel { parametersOf(FileViewerViewModel.Input(owner, name, branch, path)) }
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val pullRefreshState =
            rememberPullRefreshState(viewModel.isLoading, onRefresh = { viewModel.getRepoFile() })
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
                when (viewModel.hasError) {
                    true -> ErrorMessage(
                        message = stringResource(Res.strings.msg_file_load_error),
                        onRetryClick = viewModel::getRepoFile,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                    )

                    false -> FileContent(file)
                }

                RefreshIndicator(pullRefreshState, viewModel.isLoading)
            }
        }
    }

    @Composable
    private fun FileContent(file: RepoFile.File?) {
        when (file?.fileType?.__typename) {
            "MarkdownFileType" -> MarkdownFileViewer(file.fileType?.onMarkdownFileType!!)
            "ImageFileType" -> ImageFileViewer(file.fileType?.onImageFileType!!)
            "PdfFileType" -> PdfFileViewer(file.fileType?.onPdfFileType!!)
            "TextFileType" -> TextFileViewer(file.fileType?.onTextFileType!!, file.extension ?: "")
            else -> {}
        }
    }

    @Composable
    private fun RowScope.FileActions(file: RepoFile.File?) {
        val shareManager: ShareManager = get()
        val authManager: AuthManager = get()

        // TODO: Different actions for each file type

        IconButton(
            onClick = { shareManager.shareText("${authManager.currentAccount?.baseUrl ?: "https://github.com"}/$owner/$name/blob/$branch/$path") }
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(Res.strings.action_share)
            )
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Toolbar(
        scrollBehavior: TopAppBarScrollBehavior,
        file: RepoFile.File?,
    ) {
        SmallToolbar(
            title = path.split("/").lastOrNull() ?: "File",
            actions = { FileActions(file) },
            scrollBehavior = scrollBehavior
        )
    }

}