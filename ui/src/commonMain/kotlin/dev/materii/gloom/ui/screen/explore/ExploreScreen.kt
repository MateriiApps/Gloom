package dev.materii.gloom.ui.screen.explore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.ui.screen.explore.component.TrendingFeedFooter
import dev.materii.gloom.ui.screen.explore.component.TrendingFeedHeader
import dev.materii.gloom.ui.screen.explore.component.TrendingRepoItem
import dev.materii.gloom.ui.screen.explore.viewmodel.ExploreViewModel
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import dev.materii.gloom.ui.screen.repo.RepoScreen
import dev.materii.gloom.ui.util.NavigationUtil.navigate

class ExploreScreen: Tab {

    override val options: TabOptions
        @Composable get() {
            val navigator = LocalTabNavigator.current
            val selected = navigator.current == this
            return TabOptions(
                0u,
                stringResource(Res.strings.navigation_explore),
                rememberVectorPainter(if (selected) Icons.Filled.Explore else Icons.Outlined.Explore)
            )
        }

    @Composable
    override fun Content() = Screen()

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Screen() {
        val viewModel: ExploreViewModel = koinScreenModel()
        val navigator = LocalNavigator.currentOrThrow
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        Scaffold(
            topBar = { TopBar(scrollBehavior) }
        ) { pv ->
            PullToRefreshBox(
                isRefreshing = viewModel.isLoading,
                onRefresh = { viewModel.loadTrending() },
                modifier = Modifier
                    .padding(pv)
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item("header") {
                        TrendingFeedHeader(
                            currentTrendingPeriod = viewModel.trendingPeriod,
                            onTrendingPeriodChange = { newPeriod -> viewModel.updateTrendingPeriod(newPeriod) }
                        )
                    }

                    items(
                        viewModel.trendingRepos,
                        key = { it.id }
                    ) { trendingRepo ->
                        TrendingRepoItem(
                            trendingRepository = trendingRepo,
                            trendingPeriod = viewModel.trendingPeriod,
                            starToggleEnabled = !viewModel.repoStarsPending.contains(trendingRepo.id),
                            onClick = { navigator.navigate(RepoScreen(trendingRepo.owner.login, trendingRepo.name)) },
                            onOwnerClick = { navigator.navigate(ProfileScreen(trendingRepo.owner.login)) },
                            onStarClick = { viewModel.starRepo(trendingRepo.id) },
                            onUnstarClick = { viewModel.unstarRepo(trendingRepo.id) }
                        )
                    }

                    if (viewModel.trendingRepos.isNotEmpty()) {
                        item("footer") {
                            TrendingFeedFooter()
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
        LargeTopAppBar(
            title = {
                Text(text = options.title)
            },
            scrollBehavior = scrollBehavior
        )
    }

}