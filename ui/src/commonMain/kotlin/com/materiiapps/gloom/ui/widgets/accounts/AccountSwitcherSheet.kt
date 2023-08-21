package com.materiiapps.gloom.ui.widgets.accounts

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.components.bottomsheet.BottomSheet
import com.materiiapps.gloom.ui.components.bottomsheet.BottomSheetLayout
import com.materiiapps.gloom.ui.components.settings.SettingsButton
import com.materiiapps.gloom.ui.screens.auth.LandingScreen
import com.materiiapps.gloom.ui.screens.root.RootScreen
import com.materiiapps.gloom.ui.utils.navigate
import com.materiiapps.gloom.ui.viewmodels.settings.AccountSettingsViewModel
import dev.icerock.moko.resources.compose.stringResource
import org.koin.androidx.compose.get

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun AccountSwitcherSheet(
    onDismiss: () -> Unit,
) {
    val nav = LocalNavigator.currentOrThrow
    val viewModel: AccountSettingsViewModel = get()
    val accounts = viewModel.authManager.accounts.values
        .toList()
        .sortedByDescending { viewModel.authManager.currentAccount?.id == it.id }

    BottomSheet(onDismiss = onDismiss) {
        BottomSheetLayout(
            title = { Text(stringResource(Res.strings.settings_accounts)) },
            padding = PaddingValues(0.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
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
                                animateToDismiss()
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
                            animateToDismiss()
                            nav.navigate(LandingScreen())
                        }
                    )
                }
            }
        }
    }
}