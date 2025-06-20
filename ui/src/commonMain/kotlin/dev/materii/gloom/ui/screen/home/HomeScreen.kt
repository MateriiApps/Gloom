package dev.materii.gloom.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.ui.component.toolbar.LargeToolbar
import dev.materii.gloom.ui.screen.home.component.*
import dev.materii.gloom.ui.screen.home.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class HomeScreen: Tab {

    override val options: TabOptions
        @Composable get() {
            val navigator = LocalTabNavigator.current
            val selected = navigator.current == this
            return TabOptions(
                0u,
                stringResource(Res.strings.navigation_home),
                rememberVectorPainter(if (selected) Icons.Filled.Home else Icons.Outlined.Home)
            )
        }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val viewModel: HomeViewModel = koinScreenModel()
        val items = viewModel.items.collectAsLazyPagingItems()
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val isLoading = items.loadState.refresh == LoadState.Loading
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        val canScrollBackToTop by remember { derivedStateOf { lazyListState.canScrollBackward } }

        Scaffold(
            topBar = { TopBar(scrollBehavior) },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = canScrollBackToTop,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    SmallFloatingActionButton(onClick = {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(0)
                        }.invokeOnCompletion { viewModel.refresh(items) }
                    }) {
                        Icon(imageVector = Icons.Filled.KeyboardArrowUp, null)
                    }
                }
            }
        ) { pv ->
            PullToRefreshBox(
                isRefreshing = isLoading,
                onRefresh = { viewModel.refresh(items) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pv)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    state = lazyListState
                ) {
                    items(
                        count = items.itemCount,
                        key = items.itemKey(),
                        contentType = items.itemContentType()
                    ) { index ->
                        val item = items[index]
                        item?.let { feedItem ->
                            when {
                                feedItem.createdRepoItemFragment != null -> {
                                    val starredItem = feedItem.createdRepoItemFragment!!
                                    val starData = viewModel.starredRepos[starredItem.repository.feedRepository.id]

                                    CreatedRepoItem(
                                        item = starredItem,
                                        starData = starData,
                                        onStarClick = viewModel::starRepo,
                                        onUnstarClick = viewModel::unstarRepo
                                    )
                                }

                                feedItem.newReleaseItemFragment != null -> NewReleaseItem(item = feedItem.newReleaseItemFragment!!)

                                feedItem.followedUserFeedItemFragment != null -> {
                                    val id =
                                        feedItem.followedUserFeedItemFragment!!.followee.feedUser?.id
                                            ?: feedItem.followedUserFeedItemFragment!!.followee.feedOrg?.id!!
                                    val followData = viewModel.followedUsers[id]

                                    FollowedUserItem(
                                        item = feedItem.followedUserFeedItemFragment!!,
                                        followData = followData,
                                        onFollowClick = viewModel::followUser,
                                        onUnfollowClick = viewModel::unfollowUser
                                    )
                                }

                                feedItem.starredFeedItemFragment != null -> {
                                    val starredItem = feedItem.starredFeedItemFragment!!
                                    val starData = viewModel.starredRepos[starredItem.repository.feedRepository.id]

                                    StarredRepoItem(
                                        item = starredItem,
                                        starData = starData,
                                        onStarClick = viewModel::starRepo,
                                        onUnstarClick = viewModel::unstarRepo
                                    )
                                }

                                feedItem.recommendedRepositoryFeedItemFragment != null -> {
                                    val recommendedRepoItem = feedItem.recommendedRepositoryFeedItemFragment!!
                                    val starData = viewModel.starredRepos[recommendedRepoItem.repository.feedRepository.id]

                                    RecommendedRepoItem(
                                        item = recommendedRepoItem,
                                        starData = starData,
                                        onStarClick = viewModel::starRepo,
                                        onUnstarClick = viewModel::unstarRepo
                                    )
                                }

                                feedItem.forkedRepositoryFeedItemFragment != null -> {
                                    val forkedItem = feedItem.forkedRepositoryFeedItemFragment!!
                                    val starData = viewModel.starredRepos[forkedItem.repository.feedRepository.id]

                                    ForkedRepoItem(
                                        item = forkedItem,
                                        starData = starData,
                                        onStarClick = viewModel::starRepo,
                                        onUnstarClick = viewModel::unstarRepo
                                    )
                                }

                                feedItem.followRecommendationFeedItemFragment != null -> {
                                    val id =
                                        feedItem.followRecommendationFeedItemFragment!!.followee.feedUser?.id
                                            ?: feedItem.followRecommendationFeedItemFragment!!.followee.feedOrg?.id!!
                                    val followData = viewModel.followedUsers[id]

                                    RecommendedFollowUserItem(
                                        item = feedItem.followRecommendationFeedItemFragment!!,
                                        followData = followData,
                                        onFollowClick = viewModel::followUser,
                                        onUnfollowClick = viewModel::unfollowUser
                                    )
                                }

                                feedItem.mergedPullRequestFeedItemFragment != null -> {
                                    MergedPullRequestItem(
                                        item = feedItem.mergedPullRequestFeedItemFragment!!,
                                        onReactionClick = { reaction, unreact ->
                                            viewModel.react(feedItem.mergedPullRequestFeedItemFragment!!.pullRequest.id, reaction, unreact)
                                        }
                                    )
                                }
                            }
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
            options.title,
            scrollBehavior = scrollBehavior
        )
    }

}