package com.materiapps.gloom.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.materiapps.gloom.R
import com.materiapps.gloom.ui.components.LargeToolbar
import com.materiapps.gloom.ui.components.RefreshIndicator
import com.materiapps.gloom.ui.viewmodels.home.HomeViewModel
import com.materiapps.gloom.ui.widgets.feed.CreatedRepoItem
import com.materiapps.gloom.ui.widgets.feed.FollowedUserItem
import com.materiapps.gloom.ui.widgets.feed.ForkedRepoItem
import com.materiapps.gloom.ui.widgets.feed.NewReleaseItem
import com.materiapps.gloom.ui.widgets.feed.RecommendedFollowUserItem
import com.materiapps.gloom.ui.widgets.feed.RecommendedRepoItem
import com.materiapps.gloom.ui.widgets.feed.StarredRepoItem

class HomeScreen : Tab {
    override val options: TabOptions
        @Composable get() {
            val navigator = LocalTabNavigator.current
            val selected = navigator.current == this
            return TabOptions(
                0u,
                stringResource(R.string.navigation_home),
                rememberVectorPainter(if (selected) Icons.Filled.Home else Icons.Outlined.Home)
            )
        }

    @Composable
    override fun Content() = Screen()

    @Composable
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    private fun Screen(
        viewModel: HomeViewModel = getScreenModel()
    ) {
        val items = viewModel.items.collectAsLazyPagingItems()
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val isLoading = items.loadState.refresh == LoadState.Loading
        val refreshState = rememberPullRefreshState(isLoading, onRefresh = {
            viewModel.refresh(items)
        })

        Scaffold(
            topBar = { TopBar(scrollBehavior) }
        ) { pv ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pv)
                    .pullRefresh(refreshState)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(items) {
                        it?.let { item ->
                            // Yes I know this is ugly, cant really do anything about it.
                            if (item.createdRepoItemFragment != null) {
                                val starData =
                                    viewModel.starredRepos[item.createdRepoItemFragment.repository.feedRepository.id]
                                CreatedRepoItem(
                                    item = item.createdRepoItemFragment,
                                    starData = starData,
                                    onStarPressed = viewModel::starRepo,
                                    onUnstarPressed = viewModel::unstarRepo
                                )
                            } else if (item.newReleaseItemFragment != null) {
                                NewReleaseItem(item = item.newReleaseItemFragment)
                            } else if (item.followedUserFeedItemFragment != null) {
                                val id = item.followedUserFeedItemFragment.followee.feedUser?.id
                                    ?: item.followedUserFeedItemFragment.followee.feedOrg?.id!!
                                val followData = viewModel.followedUsers[id]
                                FollowedUserItem(
                                    item = item.followedUserFeedItemFragment,
                                    followData = followData,
                                    onFollowPressed = viewModel::followUser,
                                    onUnfollowPressed = viewModel::unfollowUser
                                )
                            } else if (item.starredFeedItemFragment != null) {
                                val starData =
                                    viewModel.starredRepos[item.starredFeedItemFragment.repository.feedRepository.id]
                                StarredRepoItem(
                                    item = item.starredFeedItemFragment,
                                    starData = starData,
                                    onStarPressed = viewModel::starRepo,
                                    onUnstarPressed = viewModel::unstarRepo
                                )
                            } else if (item.recommendedRepositoryFeedItemFragment != null) {
                                val starData =
                                    viewModel.starredRepos[item.recommendedRepositoryFeedItemFragment.repository.feedRepository.id]
                                RecommendedRepoItem(
                                    item = item.recommendedRepositoryFeedItemFragment,
                                    starData = starData,
                                    onStarPressed = viewModel::starRepo,
                                    onUnstarPressed = viewModel::unstarRepo
                                )
                            } else if (item.forkedRepositoryFeedItemFragment != null) {
                                val starData =
                                    viewModel.starredRepos[item.forkedRepositoryFeedItemFragment.repository.feedRepository.id]
                                ForkedRepoItem(
                                    item = item.forkedRepositoryFeedItemFragment,
                                    starData = starData,
                                    onStarPressed = viewModel::starRepo,
                                    onUnstarPressed = viewModel::unstarRepo
                                )
                            } else if (item.followRecommendationFeedItemFragment != null) {
                                val id =
                                    item.followRecommendationFeedItemFragment.followee.feedUser?.id
                                        ?: item.followRecommendationFeedItemFragment.followee.feedOrg?.id!!
                                val followData = viewModel.followedUsers[id]
                                RecommendedFollowUserItem(
                                    item = item.followRecommendationFeedItemFragment,
                                    followData = followData,
                                    onFollowPressed = viewModel::followUser,
                                    onUnfollowPressed = viewModel::unfollowUser
                                )
                            }
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
            options.title,
            scrollBehavior = scrollBehavior
        )
    }
}