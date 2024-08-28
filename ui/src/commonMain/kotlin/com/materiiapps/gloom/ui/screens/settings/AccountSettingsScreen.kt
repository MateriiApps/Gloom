package com.materiiapps.gloom.ui.screens.settings

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.components.RefreshIndicator
import com.materiiapps.gloom.ui.screens.settings.component.SettingsButton
import com.materiiapps.gloom.ui.components.toolbar.LargeToolbar
import com.materiiapps.gloom.ui.screens.auth.LandingScreen
import com.materiiapps.gloom.ui.screens.root.RootScreen
import com.materiiapps.gloom.ui.utils.navigate
import com.materiiapps.gloom.ui.utils.toImmutableList
import com.materiiapps.gloom.ui.screens.settings.viewmodel.AccountSettingsViewModel
import com.materiiapps.gloom.ui.screens.settings.component.account.AccountItem
import com.materiiapps.gloom.ui.screens.settings.component.account.SignOutButton
import com.materiiapps.gloom.ui.screens.settings.component.account.SignOutDialog
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
        val nav = LocalNavigator.currentOrThrow

        val pullRefreshState = rememberPullRefreshState(
            refreshing = viewModel.isLoading,
            onRefresh = { viewModel.loadAccounts() }
        )

        val accounts by remember(viewModel.authManager.accounts) {
            derivedStateOf {
                viewModel.authManager.accounts.values
                    .toList()
                    .sortedByDescending { viewModel.authManager.currentAccount?.id == it.id }
                    .toImmutableList()
            }
        }

        if (viewModel.signOutDialogOpen) {
            SignOutDialog(
                signedOut = viewModel.signedOut,
                onSignedOut = {
                    val signedOutWasCurrent = viewModel.wasCurrent
                    viewModel.closeSignOutDialog()
                    if (signedOutWasCurrent) nav.replaceAll(LandingScreen())
                },
                onDismiss = { viewModel.closeSignOutDialog() },
                onSignOutClick = {
                    if (viewModel.attemptedSignOutId == null)
                        viewModel.signOutAll()
                    else
                        viewModel.signOut(viewModel.attemptedSignOutId!!)
                }
            )
        }

        Scaffold(
            topBar = {
                Toolbar(
                    scrollBehavior = scrollBehavior,
                    isEditMode = viewModel.isEditMode,
                    onEditClick = { viewModel.isEditMode = !viewModel.isEditMode }
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { pv ->
            Box(
                modifier = Modifier
                    .padding(pv)
                    .fillMaxSize()
                    .pullRefresh(state = pullRefreshState)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        items = accounts,
                        key = { it.id }
                    ) { account ->
                        val isCurrent by remember(viewModel.authManager.currentAccount) {
                            derivedStateOf {
                                viewModel.authManager.currentAccount?.id == account.id
                            }
                        }

                        AccountItem(
                            account = account,
                            isCurrent = isCurrent,
                            isEditMode = viewModel.isEditMode,
                            onClick = {
                                if (!isCurrent) {
                                    viewModel.switchToAccount(account.id)
                                    nav.replaceAll(RootScreen())
                                }
                            },
                            signOutButton = {
                                SignOutButton(
                                    visible = viewModel.isEditMode,
                                    onClick = { viewModel.openSignOutDialog(account.id) }
                                )
                            },
                            modifier = Modifier
                                .animateItemPlacement(tween(200))
                        )
                    }
                    item(
                        key = "Add/Sign all out"
                    ) {
                        SettingsButton(
                            label = stringResource(if (viewModel.isEditMode) Res.strings.action_sign_out_all else Res.strings.action_add_account),
                            isDanger = viewModel.isEditMode,
                            onClick = {
                                if (viewModel.isEditMode) {
                                    viewModel.signOutDialogOpen = true
                                } else {
                                    nav.navigate(LandingScreen(showAccountCard = false))
                                }
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
        scrollBehavior: TopAppBarScrollBehavior,
        isEditMode: Boolean,
        onEditClick: () -> Unit
    ) {
        LargeToolbar(
            title = stringResource(Res.strings.settings_accounts),
            scrollBehavior = scrollBehavior,
            actions = {
                IconToggleButton(
                    checked = isEditMode,
                    onCheckedChange = { onEditClick() },
                    colors = IconButtonDefaults.iconToggleButtonColors(
                        checkedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                ) {
                    Icon(
                        imageVector = if (isEditMode) Icons.Filled.Edit else Icons.Outlined.Edit,
                        contentDescription = stringResource(if (isEditMode) Res.strings.action_stop_edit else Res.strings.action_edit)
                    )
                }
            }
        )
    }

}