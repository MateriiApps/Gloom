package dev.materii.gloom.ui.screen.settings.component.account

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
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.ui.component.bottomsheet.BottomSheet
import dev.materii.gloom.ui.component.bottomsheet.BottomSheetLayout
import dev.materii.gloom.ui.screen.auth.LandingScreen
import dev.materii.gloom.ui.screen.root.RootScreen
import dev.materii.gloom.ui.screen.settings.component.SettingsButton
import dev.materii.gloom.ui.screen.settings.viewmodel.AccountSettingsViewModel
import dev.materii.gloom.ui.util.NavigationUtil.navigate
import dev.materii.gloom.util.toImmutableList
import org.koin.compose.koinInject

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AccountSwitcherSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
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

    BottomSheet(
        onDismiss = onDismiss,
        modifier = modifier
    ) {
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