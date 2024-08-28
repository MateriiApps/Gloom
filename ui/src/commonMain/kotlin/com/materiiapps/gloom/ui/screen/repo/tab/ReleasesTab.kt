package com.materiiapps.gloom.ui.screen.repo.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.benasher44.uuid.uuid4
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.components.RefreshIndicator
import com.materiiapps.gloom.ui.components.ThinDivider
import com.materiiapps.gloom.ui.screen.repo.viewmodel.RepoReleasesViewModel
import com.materiiapps.gloom.ui.screen.repo.component.LatestReleaseItem
import com.materiiapps.gloom.ui.screen.repo.component.ReleaseItem
import dev.icerock.moko.resources.compose.stringResource
import org.koin.core.parameter.parametersOf

class ReleasesTab(
    private val owner: String,
    private val name: String
) : Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(1u, stringResource(Res.strings.repo_tab_releases))

    override val key = "$owner/$name-${uuid4()}"

    @Composable
    override fun Content() = Screen()

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Screen() {
        val viewModel: RepoReleasesViewModel = getScreenModel { parametersOf(owner to name) }
        val items = viewModel.items.collectAsLazyPagingItems()
        val isLoading = items.loadState.refresh == LoadState.Loading
        val pullRefreshState = rememberPullRefreshState(
            refreshing = isLoading,
            onRefresh = { items.refresh() }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .clipToBounds()
        ) {
            LazyColumn {
                val latest = items.itemSnapshotList.firstOrNull { it?.isLatest == true }

                latest?.let {
                    item {
                        Column {
                            LatestReleaseItem(owner, name, it)
                            ThinDivider()
                        }
                    }
                    item {
                        Column {
                            Text(
                                text = stringResource(Res.strings.label_all_releases),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    top = 32.dp,
                                    bottom = 16.dp,
                                    end = 16.dp
                                )
                            )

                            ThinDivider()
                        }
                    }
                }

                items(
                    count = items.itemCount,
                    key = items.itemKey(),
                    contentType = items.itemContentType()
                ) { index ->
                    items[index]?.let { release ->
                        Column {
                            ReleaseItem(owner, name, release)
                            ThinDivider()
                        }
                    }
                }
            }
            RefreshIndicator(state = pullRefreshState, isRefreshing = isLoading)
        }
    }

}