package com.materiapps.gloom.ui.screens.repo.tab

<<<<<<< HEAD
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.materiapps.gloom.R
import com.materiapps.gloom.ui.components.RefreshIndicator
import com.materiapps.gloom.ui.screens.explorer.DirectoryListingScreen
import com.materiapps.gloom.ui.viewmodels.repo.tab.RepoCodeViewModel
import com.materiapps.gloom.ui.viewmodels.repo.tab.RepoDetailsViewModel
import org.koin.core.parameter.parametersOf
import java.util.UUID

class CodeTab(
    private val owner: String,
    private val name: String
): Tab {

    override val key = "$owner/$name-${UUID.randomUUID()}"
    override val options: TabOptions
        @Composable get() = TabOptions(1u, stringResource(id = R.string.repo_tab_code))

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
                        DirectoryListingScreen(owner, name, "$it:")
                    ) { nav ->
                        SlideTransition(nav)
                    }
                }
            }
            RefreshIndicator(state = pullRefreshState, isRefreshing = viewModel.isLoading)
        }
    }

=======
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CodeTab() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Code")
    }
>>>>>>> 430f7f6 (Setup and details tab)
}