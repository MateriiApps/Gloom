package com.materiiapps.gloom.ui.screens.explorer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.domain.manager.ShareManager
import com.materiiapps.gloom.gql.fragment.RepoFile
import com.materiiapps.gloom.ui.components.BackButton
import com.materiiapps.gloom.ui.components.DownloadButton
import com.materiiapps.gloom.ui.components.ErrorMessage
import com.materiiapps.gloom.ui.components.RefreshIndicator
import com.materiiapps.gloom.ui.screens.explorer.viewers.ImageFileViewer
import com.materiiapps.gloom.ui.screens.explorer.viewers.MarkdownFileViewer
import com.materiiapps.gloom.ui.screens.explorer.viewers.PdfFileViewer
import com.materiiapps.gloom.ui.screens.explorer.viewers.TextFileViewer
import com.materiiapps.gloom.ui.utils.thenIf
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

        var topBarHidden by remember {
            mutableStateOf(false)
        }

        Scaffold(
            topBar = { Toolbar(scrollBehavior, viewModel, file, topBarHidden) },
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

                    false -> FileContent(
                        file = file,
                        viewModel = viewModel,
                        onHideToggled = {
                            topBarHidden = !topBarHidden
                        }
                    )
                }

                RefreshIndicator(pullRefreshState, viewModel.isLoading)
            }
        }
    }

    @Composable
    private fun FileContent(
        file: RepoFile.File?,
        viewModel: FileViewerViewModel,
        onHideToggled: () -> Unit
    ) {
        when (file?.fileType?.__typename) {
            "MarkdownFileType" -> MarkdownFileViewer(file.fileType?.onMarkdownFileType!!)
            "ImageFileType" -> ImageFileViewer(file.fileType?.onImageFileType!!)
            "PdfFileType" -> PdfFileViewer(file.fileType?.onPdfFileType!!)
            "TextFileType" -> TextFileViewer(
                textFile = file.fileType?.onTextFileType!!,
                extension = file.extension ?: "",
                linesSelected = viewModel.selectedLines,
                onHideToggled = onHideToggled,
                onLinesSelected = { lineNumbers, snippet ->
                    viewModel.selectedLines = lineNumbers
                    viewModel.selectedSnippet = snippet
                }
            )
            else -> {}
        }
    }

    @Composable
    private fun RowScope.FileActions(
        viewModel: FileViewerViewModel,
        file: RepoFile.File?
    ) {
        val shareManager: ShareManager = get()
        val clipboardManager = LocalClipboardManager.current

        when (file?.fileType?.__typename) {
            "MarkdownFileType" -> {}
            "ImageFileType" -> {
                DownloadButton(
                    downloadUrl = file.fileType!!.onImageFileType!!.url!!
                )
            }

            "PdfFileType" -> {
                DownloadButton(
                    downloadUrl = file.fileType!!.onPdfFileType!!.url!!
                )
            }

            "TextFileType" -> {
                if (viewModel.selectedSnippet.isNotBlank()) {
                    IconButton(
                        onClick = { clipboardManager.setText(AnnotatedString(viewModel.selectedSnippet)) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ContentCopy,
                            contentDescription = null
                        )
                    }
                }
            }

            else -> {}
        }

        IconButton(
            onClick = { shareManager.shareText(viewModel.getFileUrl()) }
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(Res.strings.action_share)
            )
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    private fun Toolbar(
        scrollBehavior: TopAppBarScrollBehavior,
        viewModel: FileViewerViewModel,
        file: RepoFile.File?,
        hidden: Boolean
    ) {
        val text = when {
            viewModel.selectedLines == null -> path.split("/").lastOrNull() ?: "File"
            else -> stringResource(
                Res.plurals.plural_lines_selected,
                viewModel.selectedLines!!.count(),
                viewModel.selectedLines!!.first,
                viewModel.selectedLines!!.last
            )
        }

        TopAppBar(
            title = {
                AnimatedContent(
                    text,
                    label = "Title Text",
                    contentKey = { viewModel.selectedLines == null },
                    transitionSpec = {
                        val direction =
                            if (viewModel.selectedLines == null) AnimatedContentTransitionScope.SlideDirection.Down else AnimatedContentTransitionScope.SlideDirection.Up
                        slideIntoContainer(direction) {
                            it * 2
                        } togetherWith slideOutOfContainer(direction) {
                            it * 2
                        }
                    },
                ) {
                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            navigationIcon = {
                if (viewModel.selectedLines != null) {
                    IconButton(
                        onClick = {
                            viewModel.selectedLines = null
                            viewModel.selectedSnippet = ""
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null
                        )
                    }
                } else {
                    BackButton()
                }
            },
            actions = { FileActions(viewModel, file) },
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = if (viewModel.selectedLines != null) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface,
                scrolledContainerColor = if (viewModel.selectedLines != null) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceColorAtElevation(
                    3.dp
                ),
            ),
            modifier = Modifier
                .animateContentSize()
                .thenIf(hidden) {
                    height(0.dp)
                }
        )
    }

}