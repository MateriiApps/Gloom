package com.materiiapps.gloom.ui.screen.repo.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.benasher44.uuid.uuid4
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.screen.explorer.DirectoryListingScreen
import com.materiiapps.gloom.ui.screen.repo.viewmodel.RepoCodeViewModel
import com.materiiapps.gloom.ui.transition.SlideTransition
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
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val viewModel: RepoCodeViewModel = koinScreenModel { parametersOf(owner to name) }

        PullToRefreshBox(
            isRefreshing = viewModel.isLoading,
            onRefresh = { viewModel.loadDefaultBranch() },
            modifier = Modifier
                .fillMaxSize()
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
        }
    }

}