package dev.materii.gloom.ui.screen.repo.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.benasher44.uuid.uuid4
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.ui.screen.explorer.DirectoryListingScreen
import dev.materii.gloom.ui.screen.repo.CommitsScreen
import dev.materii.gloom.ui.screen.repo.viewmodel.RepoCodeViewModel
import dev.materii.gloom.ui.transition.SlideTransition
import dev.materii.gloom.ui.util.NavigationUtil.navigate
import org.koin.core.parameter.parametersOf

class CodeTab(
    private val owner: String,
    private val name: String
): Tab {

    override val key = "$owner/$name-${uuid4()}"
    override val options: TabOptions
        @Composable get() = TabOptions(1u, stringResource(Res.strings.repo_tab_code))

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val viewModel: RepoCodeViewModel = koinScreenModel { parametersOf(owner, name) }
        val uiState by viewModel.uiState.collectAsState()

        PullToRefreshBox(
            isRefreshing = uiState is RepoCodeViewModel.UiState.Loading,
            onRefresh = viewModel::load,
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
        ) {
            when (val state = uiState) {
                is RepoCodeViewModel.UiState.Loaded  ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            ElevatedCard(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(state.defaultBranch)
                                }
                            }

                            ElevatedCard(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val nav = LocalNavigator.currentOrThrow

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { nav.navigate(CommitsScreen(state.id, state.defaultBranch)) }
                                        .padding(16.dp)
                                ) {
                                    Text(stringResource(Res.strings.commits))
                                }
                            }
                        }

                        Navigator(
                            screen = DirectoryListingScreen(owner, name, "${state.defaultBranch}:")
                        ) { nav ->
                            SlideTransition(nav)
                        }
                    }

                is RepoCodeViewModel.UiState.Loading -> {}

                is RepoCodeViewModel.UiState.Error   -> {
                    // TODO: Show error state
                }
            }
        }
    }

}