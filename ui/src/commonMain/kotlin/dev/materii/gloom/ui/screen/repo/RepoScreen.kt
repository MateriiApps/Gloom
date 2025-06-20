package dev.materii.gloom.ui.screen.repo

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.benasher44.uuid.uuid4
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.api.dto.user.User
import dev.materii.gloom.domain.manager.ShareManager
import dev.materii.gloom.ui.component.Avatar
import dev.materii.gloom.ui.component.BackButton
import dev.materii.gloom.ui.component.Collapsable
import dev.materii.gloom.ui.component.TextBanner
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import dev.materii.gloom.ui.screen.repo.viewmodel.RepoViewModel
import dev.materii.gloom.ui.theme.gloomColorScheme
import dev.materii.gloom.ui.util.NavigationUtil.navigate
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

class RepoScreen(
    private val owner: String,
    private val name: String
): Screen {

    override val key = "$owner/$name-${uuid4()}"

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val viewModel: RepoViewModel = koinScreenModel { parametersOf(owner to name) }
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) {
            viewModel.tabs.size
        }

        Scaffold(
            topBar = { Toolbar(scrollBehavior, viewModel) }
        ) { pv ->
            Column(
                modifier = Modifier
                    .padding(pv)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                val tabColor by animateColorAsState(
                    targetValue = MaterialTheme.colorScheme.surfaceContainer.copy(
                        alpha = scrollBehavior.state.collapsedFraction
                    ),
                    label = "Tab color"
                )
                val badgeColor by animateColorAsState(
                    targetValue = Color.Black
                        .copy(scrollBehavior.state.collapsedFraction * 0.2f)
                        .compositeOver(
                            MaterialTheme.colorScheme.secondaryContainer
                        ), label = "Badge color"
                )
                val badgeTextColor by animateColorAsState(
                    targetValue = Color.White
                        .copy(scrollBehavior.state.collapsedFraction)
                        .compositeOver(
                            LocalContentColor.current
                        ), label = "Badge text color"
                )

                Collapsable(
                    overlapFraction = scrollBehavior.state.collapsedFraction,
                    under = {
                        if (viewModel.repoOverview?.isArchived == true) {
                            TextBanner(
                                text = { Text(stringResource(Res.strings.label_repo_archived)) },
                                icon = {
                                    Icon(
                                        Icons.Outlined.Inventory2,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                },
                                backgroundColor = MaterialTheme.gloomColorScheme.warningContainer,
                                contentColor = MaterialTheme.gloomColorScheme.onWarningContainer,
                                outlineColor = MaterialTheme.gloomColorScheme.warning
                            )
                        }
                    }
                ) {
                    ScrollableTabRow(
                        selectedTabIndex = pagerState.currentPage,
                        edgePadding = 0.dp,
                        divider = {
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                                thickness = 0.5.dp,
                            )
                        },
                        containerColor = tabColor,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        viewModel.tabs.forEachIndexed { i, tab ->
                            Tab(
                                selected = pagerState.currentPage == i,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(i)
                                    }
                                },
                                text = {
                                    val badgeCount = viewModel.badgeCounts[i]
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Text(tab.options.title)
                                        if (badgeCount != null && badgeCount > 0)
                                            Text(
                                                text = badgeCount.toString(),
                                                style = MaterialTheme.typography.labelLarge,
                                                fontSize = 11.sp,
                                                lineHeight = 11.sp,
                                                textAlign = TextAlign.Center,
                                                color = badgeTextColor,
                                                modifier = Modifier
                                                    .widthIn(21.dp)
                                                    .clip(CircleShape)
                                                    .background(badgeColor)
                                                    .padding(5.dp, 4.dp)
                                            )
                                    }
                                }
                            )
                        }
                    }
                }

                HorizontalPager(
                    state = pagerState
                ) {
                    val tab = viewModel.tabs[it]

                    Box(
                        Modifier
                            .fillMaxSize()
                    ) {
                        tab.Content()
                    }
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Toolbar(
        scrollBehavior: TopAppBarScrollBehavior,
        viewModel: RepoViewModel
    ) {
        val shareManager: ShareManager = koinInject()
        val avSize = Dp(55 - scrollBehavior.state.collapsedFraction * 55)
        val nav = LocalNavigator.currentOrThrow
        val loading by remember {
            derivedStateOf {
                viewModel.repoOverviewLoading
            }
        }

        LargeTopAppBar(
            title = {
                if (!loading) {
                    if (!viewModel.hasError && viewModel.repoOverview != null) {
                        val it = viewModel.repoOverview!!
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (avSize != 0.dp) {
                                Avatar(
                                    url = it.owner.avatarUrl,
                                    contentDescription = stringResource(
                                        Res.strings.noun_users_avatar,
                                        it.owner.login
                                    ),
                                    type = User.Type.fromTypeName(it.owner.__typename),
                                    modifier = Modifier
                                        .size(avSize)
                                        .clickable { nav.navigate(ProfileScreen(it.owner.login)) }
                                )
                            }

                            Column {
                                Text(
                                    text = it.owner.login,
                                    style = MaterialTheme.typography.labelLarge,
                                    maxLines = 1,
                                    modifier = Modifier
                                        .basicMarquee(Int.MAX_VALUE)
                                        .clickable {
                                            nav.navigate(ProfileScreen(it.owner.login))
                                        }
                                )
                                Text(
                                    text = it.name,
                                    maxLines = 1,
                                    modifier = Modifier.basicMarquee(Int.MAX_VALUE)
                                )
                            }
                        }
                    } else Text("Error loading repository")
                } else {
                    Box(modifier = Modifier.size(55.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            },
            scrollBehavior = scrollBehavior,
            navigationIcon = { BackButton() },
            actions = {
                viewModel.repoOverview?.let {
                    IconButton(onClick = { shareManager.shareText("https://github.com/${it.owner.login}/${it.name}") }) {
                        Icon(Icons.Filled.Share, stringResource(Res.strings.action_share))
                    }
                }
            }
        )
    }
}