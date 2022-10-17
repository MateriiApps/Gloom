package com.materiapps.gloom.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.materiapps.gloom.R
import com.materiapps.gloom.RepoListQuery
import com.materiapps.gloom.ui.components.LargeToolbar
import com.materiapps.gloom.ui.viewmodels.list.RepositoryListViewModel
import org.koin.core.parameter.parametersOf

class RepositoryListScreen(
    private val username: String
) : Screen {

    @Composable
    override fun Content() = Screen()

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Screen(
        viewModel: RepositoryListViewModel = getScreenModel { parametersOf(username) }
    ) {
        val repos = viewModel.repos.collectAsLazyPagingItems()
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val isLoading = repos.loadState.refresh == LoadState.Loading

        Scaffold(
            topBar = { TopBar(scrollBehavior) }
        ) { pv ->
            BoxWithConstraints {
                if (isLoading) {
                    Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                if (maxWidth > 400.dp) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(256.dp),
                        contentPadding = pv,
                        modifier = Modifier
                            .nestedScroll(scrollBehavior.nestedScrollConnection)
                    ) {
                        items(repos.itemCount) {
                            if (repos[it] != null) RepoItem(repo = repos[it]!!)
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(pv)
                            .nestedScroll(scrollBehavior.nestedScrollConnection)
                    ) {
                        items(repos) {
                            if (it != null) RepoItem(repo = it)
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
            title = stringResource(R.string.repos),
            showBackButton = true,
            scrollBehavior = scrollBehavior
        )
    }

    @Composable
    private fun RepoItem(repo: RepoListQuery.Node) {
        Column(
            Modifier
                .clickable { }
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = repo.name,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 16.sp
                )
            )
            repo.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                    )
                )
            }
            if (repo.isFork) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 5.dp)
                ) {
                    Icon(
                        painterResource(R.drawable.ic_fork_24),
                        contentDescription = "Forked Repository",
                        modifier = Modifier.size(15.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                    )
                    Text(
                        text = stringResource(R.string.forked_from, repo.parent!!.nameWithOwner),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                        )
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Star,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Color(0xFFF1E05A)
                        )
                        Text(text = repo.stargazerCount.toString())
                    }


                    if (repo.languages?.nodes?.isNotEmpty() == true) {
                        val lang = repo.languages.nodes[0]
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Circle,
                                contentDescription = "Forked Repository",
                                modifier = Modifier.size(15.dp),
                                tint = Color(android.graphics.Color.parseColor(lang?.color))
                            )
                            Text(text = lang?.name ?: "")
                        }
                    }
                }
            }
        }
    }
}