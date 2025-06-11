package dev.materii.gloom.ui.screen.explorer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.FormatColorText
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.domain.manager.ShareManager
import dev.materii.gloom.gql.fragment.RepoFile
import dev.materii.gloom.ui.component.BackButton
import dev.materii.gloom.ui.component.DownloadButton
import dev.materii.gloom.ui.component.ErrorMessage
import dev.materii.gloom.ui.screen.explorer.viewers.ImageFileViewer
import dev.materii.gloom.ui.screen.explorer.viewers.MarkdownFileViewer
import dev.materii.gloom.ui.screen.explorer.viewers.PdfFileViewer
import dev.materii.gloom.ui.screen.explorer.viewers.TextFileViewer
import dev.materii.gloom.ui.screen.explorer.viewmodel.FileViewerViewModel
import dev.materii.gloom.ui.util.thenIf
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

class FileViewerScreen(
    private val owner: String,
    private val name: String,
    private val branch: String,
    private val path: String
): Screen {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val viewModel: FileViewerViewModel = koinScreenModel { parametersOf(FileViewerViewModel.Input(owner, name, branch, path)) }
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val file = viewModel.file?.gitObject?.onCommit?.file

        var topBarHidden by remember {
            mutableStateOf(false)
        }

        Scaffold(
            topBar = { Toolbar(scrollBehavior, viewModel, file, topBarHidden) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { pv ->
            PullToRefreshBox(
                isRefreshing = viewModel.isLoading,
                onRefresh = { viewModel.refresh() },
                modifier = Modifier
                    .padding(pv)
                    .fillMaxSize()
            ) {
                when (viewModel.fileHasError) {
                    true -> ErrorMessage(
                        message = stringResource(Res.strings.msg_file_load_error),
                        onRetryClick = viewModel::refresh,
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
            "MarkdownFileType" -> {
                MarkdownFileViewer(
                    markdownFile = file.fileType?.onMarkdownFileType!!,
                    rawFile = viewModel.rawMarkdown,
                    showRaw = viewModel.showRawMarkdown,
                    rawHasError = viewModel.rawMarkdownHasError,
                    linesSelected = viewModel.selectedLines,
                    onHideToggled = onHideToggled,
                    onLinesSelected = { lineNumbers, snippet ->
                        viewModel.selectedLines = lineNumbers
                        viewModel.selectedSnippet = snippet
                    }
                )
            }

            "ImageFileType"    -> ImageFileViewer(file.fileType?.onImageFileType!!)
            "PdfFileType"      -> PdfFileViewer(file.fileType?.onPdfFileType!!)

            "TextFileType"     -> {
                file.fileType?.onTextFileType?.contentRaw?.let { content ->
                    TextFileViewer(
                        content = content,
                        extension = file.extension ?: "",
                        linesSelected = viewModel.selectedLines,
                        onHideToggled = onHideToggled,
                        onLinesSelected = { lineNumbers, snippet ->
                            viewModel.selectedLines = lineNumbers
                            viewModel.selectedSnippet = snippet
                        }
                    )
                }
            }

            else               -> {}
        }
    }

    @Composable
    private fun RowScope.FileActions(
        viewModel: FileViewerViewModel,
        file: RepoFile.File?
    ) {
        val shareManager: ShareManager = koinInject()
        val clipboardManager = LocalClipboardManager.current
        val fileType = file?.fileType?.__typename

        if (fileType == "MarkdownFileType") {
            IconButton(
                onClick = {
                    viewModel.toggleMarkdown()
                }
            ) {
                val (icon, contentDescription) = if (!viewModel.showRawMarkdown) {
                    Icons.Outlined.Description to Res.strings.action_view_raw
                } else {
                    Icons.Outlined.FormatColorText to Res.strings.action_view_markdown
                }

                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(contentDescription)
                )
            }
        }

        if (fileType == "ImageFileType" || fileType == "PdfFileType") {
            val url = file.fileType!!.onImageFileType?.url ?: file.fileType!!.onPdfFileType!!.url
            if (url != null) {
                DownloadButton(
                    downloadUrl = url
                )
            }
        }

        if (viewModel.selectedSnippet.isNotBlank()) {
            IconButton(
                onClick = { clipboardManager.setText(AnnotatedString(viewModel.selectedSnippet)) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ContentCopy,
                    contentDescription = stringResource(Res.strings.action_copy)
                )
            }
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
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Toolbar(
        scrollBehavior: TopAppBarScrollBehavior,
        viewModel: FileViewerViewModel,
        file: RepoFile.File?,
        hidden: Boolean
    ) {
        val text = when {
            viewModel.selectedLines == null -> path.split("/").lastOrNull() ?: "File"
            else                            -> stringResource(
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