package com.materiapps.gloom.ui.screens.list.base

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import cafe.adriel.voyager.core.screen.Screen
import com.apollographql.apollo3.api.Query
import com.materiapps.gloom.ui.components.LargeToolbar
import com.materiapps.gloom.ui.components.RefreshIndicator
import com.materiapps.gloom.ui.viewmodels.list.base.BaseListViewModel

abstract class BaseListScreen<I : Any, D : Query.Data?, VM : BaseListViewModel<I, D>> : Screen {

    @get:Composable
    abstract val viewModel: VM

    @get:StringRes
    abstract val titleRes: Int

    @Composable
    abstract fun Item(item: I)

    @Composable
    override fun Content() = Screen()

    @Composable
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    private fun Screen() {
        val items = viewModel.items.collectAsLazyPagingItems()
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val isLoading = items.loadState.refresh == LoadState.Loading
        val refreshState = rememberPullRefreshState(isLoading, onRefresh = {
            items.refresh()
        })

        Scaffold(
            topBar = { TopBar(scrollBehavior) }
        ) { pv ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pv)
                    .pullRefresh(refreshState)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                ) {
                    items(items) {
                        if (it != null) {
                            Item(it)
                            Divider(
                                color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                                thickness = 0.5.dp,
                            )
                        }
                    }
                }

                RefreshIndicator(refreshState, isRefreshing = isLoading)
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun TopBar(
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        LargeToolbar(
            title = stringResource(titleRes),
            scrollBehavior = scrollBehavior
        )
    }
}