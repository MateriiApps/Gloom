package com.materiiapps.gloom.ui.screens.repo.tab

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.benasher44.uuid.uuid4
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.components.RefreshIndicator
import com.materiiapps.gloom.ui.screens.explorer.DirectoryListingScreen
import com.materiiapps.gloom.ui.transitions.SlideTransition
import com.materiiapps.gloom.ui.screens.repo.viewmodel.RepoCodeViewModel
import dev.icerock.moko.resources.compose.stringResource
import org.koin.core.parameter.parametersOf

class CodeTab(
    private val owner: String,
    private val name: String
) : Tab {

    override val key = "$owner/$name-${uuid4()}"
    override val options: TabOptions
        @Composable get() = TabOptions(1u, stringResource(Res.strings.repo_tab_code))

    @Composable
    override fun Content() = Screen()

    @OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
    @Composable
    fun Screen(
        viewModel: RepoCodeViewModel = getScreenModel { parametersOf(owner to name) }
    ) {
        val pullRefreshState = rememberPullRefreshState(
            refreshing = viewModel.isLoading,
            onRefresh = { viewModel.loadDefaultBranch() }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .clipToBounds()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                viewModel.defaultBranch?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = it
                                )
                            }
                        }
                    }

                    Navigator(
                        DirectoryListingScreen(
                            owner,
                            name,
                            "$it:"
                        )
                    ) { nav ->
                        SlideTransition(nav)
                    }
                }
            }
            RefreshIndicator(state = pullRefreshState, isRefreshing = viewModel.isLoading)
        }
    }

}