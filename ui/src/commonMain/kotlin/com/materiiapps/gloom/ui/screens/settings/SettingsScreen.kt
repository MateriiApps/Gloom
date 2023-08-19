package com.materiiapps.gloom.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.components.LargeToolbar
import com.materiiapps.gloom.ui.components.settings.SettingsButton
import com.materiiapps.gloom.ui.components.settings.SettingsCategory
import com.materiiapps.gloom.ui.viewmodels.settings.SettingsViewModel
import dev.icerock.moko.resources.compose.stringResource

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
                    text = stringResource(Res.strings.settings_appearance),
                    subtext = stringResource(Res.strings.settings_appearance_description),
                    destination = ::AppearanceSettingsScreen
                )

                SettingsCategory(
                    icon = Icons.Outlined.AccountCircle,
                    text = stringResource(Res.strings.settings_accounts),
                    subtext = stringResource(Res.strings.settings_accounts_description),
                    destination = ::AccountSettingsScreen
                )

                SettingsButton(label = stringResource(Res.strings.action_sign_out)) {
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
            title = stringResource(Res.strings.navigation_settings),
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
            nav?.replaceAll(com.materiiapps.gloom.ui.screens.auth.LandingScreen())
        }

        AlertDialog(
            onDismissRequest = { viewModel.closeSignOutDialog() },
            title = { Text(stringResource(Res.strings.settings_sign_out_header)) },
            text = { Text(stringResource(Res.strings.settings_sign_out_text)) },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.signOut()
                    }
                ) {
                    Text(stringResource(Res.strings.action_sign_out))
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.closeSignOutDialog() }) {
                    Text(text = stringResource(Res.strings.dismiss_nevermind))
                }
            }
        )
    }

}