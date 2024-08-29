package com.materiiapps.gloom.ui.screen.list.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import cafe.adriel.voyager.core.screen.Screen
import com.apollographql.apollo.api.Query
import com.materiiapps.gloom.ui.component.RefreshIndicator
import com.materiiapps.gloom.ui.component.ThinDivider
import com.materiiapps.gloom.ui.component.toolbar.LargeToolbar
import com.materiiapps.gloom.ui.screen.list.viewmodel.BaseListViewModel
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

abstract class BaseListScreen<I : Any, D : Query.Data?, VM : BaseListViewModel<I, D>> : Screen {

    @get:Composable
    abstract val viewModel: VM

    abstract val titleRes: StringResource

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
                    items(
                        count = items.itemCount,
                        key = items.itemKey(),
                        contentType = items.itemContentType(
                        )
                    ) { index ->
                        val item = items[index]
                        if (item != null) {
                            Item(item)
                            ThinDivider()
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