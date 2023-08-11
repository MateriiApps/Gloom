package com.materiiapps.gloom.ui.screens.repo.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
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
import com.materiiapps.gloom.ui.viewmodels.repo.tab.RepoIssuesViewModel
import com.materiiapps.gloom.ui.widgets.repo.IssueItem
import dev.icerock.moko.resources.compose.stringResource
import org.koin.core.parameter.parametersOf

class IssuesTab(
    private val owner: String,
    private val name: String
) : Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(1u, stringResource(Res.strings.repo_tab_issues))

    override val key = "$owner/$name-${uuid4()}"

    @Composable
    override fun Content() = Screen()

    @Composable
    @OptIn(ExperimentalMaterialApi::class)
    fun Screen() {
        val viewModel: RepoIssuesViewModel = getScreenModel { parametersOf(owner to name) }
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
                items(
                    count = items.itemCount,
                    key = items.itemKey(),
                    contentType = items.itemContentType()
                ) { index ->
                    items[index]?.let { issue ->
                        Column {
                            IssueItem(issue)
                            Divider(
                                color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                                thickness = 0.5.dp,
                            )
                        }
                    }
                }
            }
            RefreshIndicator(state = pullRefreshState, isRefreshing = isLoading)
        }
    }

}