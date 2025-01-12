package dev.materii.gloom.ui.screen.repo.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.benasher44.uuid.uuid4
import dev.materii.gloom.Res
import dev.materii.gloom.ui.component.ThinDivider
import dev.materii.gloom.ui.screen.repo.component.IssueItem
import dev.materii.gloom.ui.screen.repo.viewmodel.RepoIssuesViewModel
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
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val viewModel: RepoIssuesViewModel = koinScreenModel { parametersOf(owner to name) }
        val items = viewModel.items.collectAsLazyPagingItems()
        val isLoading = items.loadState.refresh == LoadState.Loading

        PullToRefreshBox(
            isRefreshing = isLoading,
            onRefresh = { items.refresh() },
            modifier = Modifier
                .fillMaxSize()
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
                            ThinDivider()
                        }
                    }
                }
            }
        }
    }

}