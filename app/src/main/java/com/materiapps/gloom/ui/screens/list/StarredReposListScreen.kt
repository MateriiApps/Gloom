package com.materiapps.gloom.ui.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.materiapps.gloom.R
import com.materiapps.gloom.ui.components.LargeToolbar
import com.materiapps.gloom.ui.viewmodels.list.StarredReposListViewModel
import com.materiapps.gloom.ui.widgets.repo.RepoItem
import org.koin.core.parameter.parametersOf

class StarredReposListScreen(
    private val username: String
) : Screen {

    @Composable
    override fun Content() = Screen()

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Screen(
        viewModel: StarredReposListViewModel = getScreenModel { parametersOf(username) }
    ) {
        val repos = viewModel.repos.collectAsLazyPagingItems()
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val isLoading = repos.loadState.refresh == LoadState.Loading

        Scaffold(
            topBar = { TopBar(scrollBehavior) }
        ) { pv ->
            LazyColumn(
                modifier = Modifier
                    .padding(pv)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                items(repos) {
                    if (it != null) {
                        RepoItem(repo = it)
                        Divider(
                            color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                            thickness = 0.5.dp,
                        )
                    }
                }
                if (isLoading) item {
                    Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
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
            title = stringResource(R.string.noun_starred),
            showBackButton = true,
            scrollBehavior = scrollBehavior
        )
    }
}