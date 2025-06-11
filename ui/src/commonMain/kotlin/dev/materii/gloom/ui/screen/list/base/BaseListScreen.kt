package dev.materii.gloom.ui.screen.list.base

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import cafe.adriel.voyager.core.screen.Screen
import com.apollographql.apollo.api.Query
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.ui.component.ThinDivider
import dev.materii.gloom.ui.component.toolbar.LargeToolbar
import dev.materii.gloom.ui.screen.list.viewmodel.BaseListViewModel

abstract class BaseListScreen<I: Any, D: Query.Data?, VM: BaseListViewModel<I, D>>: Screen {

    @get:Composable
    abstract val viewModel: VM

    abstract val titleRes: StringResource

    @Composable
    abstract fun Item(item: I)

    @Composable
    override fun Content() = Screen()

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Screen() {
        val items = viewModel.items.collectAsLazyPagingItems()
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val isLoading = items.loadState.refresh == LoadState.Loading

        Scaffold(
            topBar = { TopBar(scrollBehavior) }
        ) { pv ->
            PullToRefreshBox(
                isRefreshing = isLoading,
                onRefresh = { items.refresh() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pv)
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