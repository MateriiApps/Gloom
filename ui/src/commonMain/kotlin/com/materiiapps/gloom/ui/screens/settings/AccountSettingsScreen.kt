package com.materiiapps.gloom.ui.screens.settings

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.components.LargeToolbar
import com.materiiapps.gloom.ui.components.RefreshIndicator
import com.materiiapps.gloom.ui.components.settings.SettingsButton
import com.materiiapps.gloom.ui.screens.auth.LandingScreen
import com.materiiapps.gloom.ui.screens.root.RootScreen
import com.materiiapps.gloom.ui.utils.navigate
import com.materiiapps.gloom.ui.viewmodels.settings.AccountSettingsViewModel
import com.materiiapps.gloom.ui.widgets.accounts.AccountItem
import dev.icerock.moko.resources.compose.stringResource

class AccountSettingsScreen : Screen {

    @Composable
    @OptIn(
        ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
        ExperimentalFoundationApi::class
    )
    override fun Content() {
        val viewModel: AccountSettingsViewModel = getScreenModel()
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val pullRefreshState = rememberPullRefreshState(
            refreshing = viewModel.isLoading,
            onRefresh = { viewModel.loadAccounts() })
        val nav = LocalNavigator.currentOrThrow
        val accounts = viewModel.authManager.accounts.values
            .toList()
            .sortedByDescending { viewModel.authManager.currentAccount?.id == it.id }

        Scaffold(
            topBar = { Toolbar(scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { pv ->
            Box(
                modifier = Modifier
                    .padding(pv)
                    .fillMaxSize()
                    .pullRefresh(state = pullRefreshState)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        count = accounts.size,
                        key = { accounts[it].id }
                    ) {
                        accounts[it].let { account ->
                            val isCurrent = viewModel.authManager.currentAccount?.id == account.id
                            AccountItem(
                                account = account,
                                isCurrent = isCurrent,
                                onClick = {
                                    if (!isCurrent) {
                                        viewModel.switchToAccount(account.id)
                                        nav.replaceAll(RootScreen())
                                    }
                                },
                                modifier = Modifier.animateItemPlacement(tween(200))
                            )
                        }
                    }
                    item {
                        SettingsButton(
                            label = stringResource(Res.strings.action_add_account),
                            onClick = {
                                nav.navigate(LandingScreen())
                            }
                        )
                    }
                }
                RefreshIndicator(state = pullRefreshState, isRefreshing = viewModel.isLoading)
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Toolbar(
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        LargeToolbar(
            title = stringResource(Res.strings.settings_accounts),
            scrollBehavior = scrollBehavior
        )
    }

}