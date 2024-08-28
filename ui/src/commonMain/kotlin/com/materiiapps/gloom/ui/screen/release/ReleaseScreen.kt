package com.materiiapps.gloom.ui.screen.release

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.CheckCircleOutline
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.benasher44.uuid.uuid4
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.domain.manager.DialogManager
import com.materiiapps.gloom.domain.manager.DialogState
import com.materiiapps.gloom.domain.manager.ShareManager
import com.materiiapps.gloom.gql.fragment.ReleaseDetails
import com.materiiapps.gloom.ui.components.BackButton
import com.materiiapps.gloom.ui.components.RefreshIndicator
import com.materiiapps.gloom.ui.components.ThinDivider
import com.materiiapps.gloom.ui.theme.colors
import com.materiiapps.gloom.ui.screen.release.viewmodel.ReleaseViewModel
import com.materiiapps.gloom.ui.widgets.Markdown
import com.materiiapps.gloom.ui.widgets.alerts.LocalAlertController
import com.materiiapps.gloom.ui.widgets.reaction.ReactionRow
import com.materiiapps.gloom.ui.screen.release.component.ReleaseAsset
import com.materiiapps.gloom.ui.screen.release.component.ReleaseAuthor
import com.materiiapps.gloom.ui.screen.release.component.ReleaseContributors
import com.materiiapps.gloom.ui.screen.release.component.ReleaseHeader
import com.materiiapps.gloom.ui.screen.release.component.ReleaseInfo
import com.materiiapps.gloom.ui.screen.release.dialog.ReleaseAssetInstallDialog
import com.materiiapps.gloom.utils.Feature
import com.materiiapps.gloom.utils.Features
import dev.icerock.moko.resources.compose.stringResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

class ReleaseScreen(
    val owner: String,
    val name: String,
    val tag: String
) : Screen {

    override val key = "$owner/$name-$tag-${uuid4()}"

    @Composable
    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
    override fun Content() {
        val viewModel: ReleaseViewModel = getScreenModel { parametersOf(Triple(owner, name, tag)) }
        val alertController = LocalAlertController.current
        val dialogManager: DialogManager = koinInject()
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val items = viewModel.items.collectAsLazyPagingItems()
        val details = viewModel.details
        val isLoading = items.loadState.refresh == LoadState.Loading
        val pullRefreshState = rememberPullRefreshState(
            refreshing = isLoading,
            onRefresh = { items.refresh() }
        )

        Scaffold(
            topBar = {
                TitleBar(
                    details = viewModel.details,
                    isLoading = isLoading,
                    scrollBehavior = scrollBehavior
                )
            }
        ) { pv ->
            Box(
                modifier = Modifier
                    .padding(pv)
                    .pullRefresh(pullRefreshState)
                    .fillMaxSize()
                    .clipToBounds()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                if (viewModel.apkFile != null && Features.contains(Feature.INSTALL_APKS)) {
                    when (dialogManager.installApk) {
                        DialogState.UNKNOWN -> {
                            ReleaseAssetInstallDialog(
                                fileName = viewModel.apkFile!!.name,
                                onClose = { dontShowAgain ->
                                    viewModel.clearApk()
                                    if (dontShowAgain == true) dialogManager.installApk =
                                        DialogState.DENIED
                                },
                                onConfirm = { dontShowAgain ->
                                    viewModel.installApk()
                                    viewModel.clearApk()
                                    if (dontShowAgain) dialogManager.installApk =
                                        DialogState.CONFIRMED
                                }
                            )
                        }

                        DialogState.CONFIRMED -> viewModel.installApk()
                        else -> {}
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    details?.let {
                        item {
                            ReleaseHeader(it)
                        }
                    }
                    details?.author?.let { author ->
                        item {
                            ReleaseAuthor(
                                login = author.login,
                                avatarUrl = author.avatarUrl,
                                timestamp = details.createdAt
                            )
                        }
                    }
                    details?.descriptionHTML?.let { description ->
                        if (description.isNotBlank()) {
                            item {
                                Markdown(
                                    text = description,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                            }
                        }
                    }
                    details?.reactionGroups?.map { it.reaction }?.let {
                        item {
                            ReactionRow(
                                reactions = it,
                                onReactionClick = { reaction, unreact ->
                                    viewModel.react(reaction, unreact)
                                },
                                forRelease = true
                            )
                        }
                    }
                    if (details != null) {
                        item {
                            ThinDivider()
                        }
                    }
                    details?.mentions?.nodes?.filterNotNull()?.map { it.login to it.avatarUrl }
                        ?.let {
                            if (it.isNotEmpty()) {
                                item {
                                    Text(
                                        text = stringResource(Res.strings.noun_contributors),
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colors.primary,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                                item {
                                    ReleaseContributors(contributors = it)
                                }
                                item {
                                    ThinDivider()
                                }
                            }
                        }
                    details?.let {
                        item {
                            ReleaseInfo(
                                tagName = it.tagName,
                                commit = it.tagCommit?.abbreviatedOid
                            )
                        }
                        item {
                            ThinDivider()
                        }
                    }
                    items(
                        count = items.itemCount,
                        key = items.itemKey(),
                        contentType = items.itemContentType()
                    ) {
                        items[it]?.let { asset ->
                            ReleaseAsset(
                                name = asset.name,
                                size = asset.size,
                                onDownloadClick = {
                                    alertController.showText(
                                        "Downloading ${asset.name}...",
                                        icon = Icons.Filled.Download
                                    )
                                    viewModel.downloadAsset(
                                        asset.downloadUrl,
                                        asset.contentType
                                    ) {
                                        alertController.showText(
                                            "Download completed",
                                            icon = Icons.Outlined.CheckCircleOutline
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
                RefreshIndicator(state = pullRefreshState, isRefreshing = isLoading)
            }
        }
    }

    @Composable
    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    fun TitleBar(
        details: ReleaseDetails?,
        isLoading: Boolean,
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        val titleAlpha = scrollBehavior.state.overlappedFraction
        val shareManager: ShareManager = koinInject()

        TopAppBar(
            navigationIcon = { BackButton() },
            scrollBehavior = scrollBehavior,
            actions = {
                details?.let {
                    IconButton(onClick = { shareManager.shareText(it.url) }) {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = stringResource(Res.strings.action_share)
                        )
                    }
                }
            },
            title = {
                when {
                    !isLoading && details != null -> {
                        Column(
                            modifier = Modifier
                                .graphicsLayer(alpha = titleAlpha)
                        ) {
                            Text(
                                buildAnnotatedString {
                                    append(details.repository.owner.login)
                                    withStyle(
                                        SpanStyle(
                                            color = MaterialTheme.colorScheme.onSurface.copy(
                                                0.5f
                                            )
                                        )
                                    ) {
                                        append(" / ")
                                    }
                                    append(details.repository.name)
                                },
                                style = MaterialTheme.typography.labelLarge
                            )
                            Text(
                                text = details.name ?: details.tagName,
                                maxLines = 1,
                                modifier = Modifier.basicMarquee(Int.MAX_VALUE)
                            )
                        }
                    }
                }
            }
        )
    }

}