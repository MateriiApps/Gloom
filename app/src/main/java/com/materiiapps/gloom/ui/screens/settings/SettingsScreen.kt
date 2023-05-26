package com.materiiapps.gloom.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.materiiapps.gloom.R
import com.materiiapps.gloom.ui.components.LargeToolbar
import com.materiiapps.gloom.ui.components.settings.SettingsButton
import com.materiiapps.gloom.ui.components.settings.SettingsCategory
import com.materiiapps.gloom.ui.screens.auth.LandingScreen
import com.materiiapps.gloom.ui.viewmodels.settings.SettingsViewModel

class SettingsScreen : Screen {

    @Composable
    override fun Content() = Screen()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Screen(
        viewModel: SettingsViewModel = getScreenModel()
    ) {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        if (viewModel.signOutDialogOpened) SignOutDialog(viewModel)

        Scaffold(
            topBar = { Toolbar(scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {

                SettingsCategory(
                    icon = Icons.Outlined.Palette,
                    text = stringResource(R.string.settings_appearance),
                    subtext = stringResource(R.string.settings_appearance_description),
                    destination = ::AppearanceSettingsScreen
                )

//                TODO: Account Switcher
//                SettingsCategory(
//                    icon = Icons.Outlined.AccountCircle,
//                    text = stringResource(R.string.settings_accounts),
//                    subtext = stringResource(R.string.settings_accounts_description)
//                )

                SettingsButton(label = stringResource(R.string.action_sign_out)) {
                    viewModel.openSignOutDialog()
                }

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Toolbar(
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        LargeToolbar(
            title = stringResource(R.string.navigation_settings),
            scrollBehavior = scrollBehavior
        )
    }

    @Composable
    private fun SignOutDialog(
        viewModel: SettingsViewModel
    ) {
        val nav = LocalNavigator.current

        if (viewModel.signedOut) {
            viewModel.closeSignOutDialog()
            nav?.replaceAll(LandingScreen())
        }

        AlertDialog(
            onDismissRequest = { viewModel.closeSignOutDialog() },
            title = { Text(stringResource(R.string.settings_sign_out_header)) },
            text = { Text(stringResource(R.string.settings_sign_out_text)) },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.signOut()
                    }
                ) {
                    Text(stringResource(R.string.action_sign_out))
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.closeSignOutDialog() }) {
                    Text(text = stringResource(R.string.dismiss_nevermind))
                }
            }
        )
    }

}