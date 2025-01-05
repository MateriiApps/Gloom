package com.materiiapps.gloom.ui.screen.settings.component.account

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.component.bottomsheet.BottomSheet
import com.materiiapps.gloom.ui.component.bottomsheet.BottomSheetLayout
import com.materiiapps.gloom.ui.screen.settings.component.SettingsButton
import com.materiiapps.gloom.ui.screen.auth.LandingScreen
import com.materiiapps.gloom.ui.screen.root.RootScreen
import com.materiiapps.gloom.ui.util.navigate
import com.materiiapps.gloom.ui.util.toImmutableList
import com.materiiapps.gloom.ui.screen.settings.viewmodel.AccountSettingsViewModel
import dev.icerock.moko.resources.compose.stringResource
import org.koin.compose.koinInject

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AccountSwitcherSheet(
    onDismiss: () -> Unit,
) {
    val nav = LocalNavigator.currentOrThrow
    val viewModel: AccountSettingsViewModel = koinInject()
    val accounts by remember(viewModel.authManager.accounts) {
        derivedStateOf {
            viewModel.authManager.accounts.values
                .toList()
                .sortedByDescending { viewModel.authManager.currentAccount?.id == it.id }
                .toImmutableList()
        }
    }

    BottomSheet(onDismiss = onDismiss) {
        BottomSheetLayout(
            title = { Text(stringResource(Res.strings.settings_accounts)) },
            padding = PaddingValues(0.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
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
                        onClick = {
                            animateToDismiss()
                            if (!isCurrent) {
                                viewModel.switchToAccount(account.id)
                                nav.replaceAll(RootScreen())
                            }
                        },
                        modifier = Modifier.animateItem(tween(200))
                    )
                }
                item(
                    key = "Add account"
                ) {
                    SettingsButton(
                        label = stringResource(Res.strings.action_add_account),
                        onClick = {
                            animateToDismiss()
                            nav.navigate(LandingScreen(showAccountCard = false))
                        }
                    )
                }
            }
        }
    }
}