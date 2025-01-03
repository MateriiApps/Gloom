package com.materiiapps.gloom.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.DesignServices
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
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
import com.materiiapps.gloom.ui.component.toolbar.LargeToolbar
import com.materiiapps.gloom.ui.screen.auth.LandingScreen
import com.materiiapps.gloom.ui.screen.settings.about.AboutScreen
import com.materiiapps.gloom.ui.screen.settings.component.SettingsButton
import com.materiiapps.gloom.ui.screen.settings.component.SettingsCategory
import com.materiiapps.gloom.ui.screen.settings.component.account.SignOutDialog
import com.materiiapps.gloom.ui.screen.settings.developer.DeveloperSettingsScreen
import com.materiiapps.gloom.ui.screen.settings.viewmodel.SettingsViewModel
import com.materiiapps.gloom.util.Feature
import com.materiiapps.gloom.util.Features
import com.materiiapps.gloom.util.IsDeveloper
import com.materiiapps.gloom.util.VersionName
import dev.icerock.moko.resources.compose.stringResource

class SettingsScreen : Screen {

    @Composable
    override fun Content() = Screen()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Screen(
        viewModel: SettingsViewModel = getScreenModel()
    ) {
        val nav = LocalNavigator.currentOrThrow
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        if (viewModel.signOutDialogOpened) {
            SignOutDialog(
                signedOut = viewModel.signedOut,
                onSignedOut = {
                    viewModel.closeSignOutDialog()
                    nav.replaceAll(LandingScreen())
                },
                onDismiss = { viewModel.closeSignOutDialog() },
                onSignOutClick = { viewModel.signOut() }
            )
        }

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

                if (Features.contains(Feature.CHANGE_ICON)) {
                    SettingsCategory(
                        icon = Icons.Outlined.DesignServices,
                        text = stringResource(Res.strings.settings_app_icon),
                        subtext = stringResource(Res.strings.settings_app_icon_description),
                        destination = ::AppIconsSettingsScreen
                    )
                }

                SettingsCategory(
                    icon = Icons.Outlined.AccountCircle,
                    text = stringResource(Res.strings.settings_accounts),
                    subtext = stringResource(Res.strings.settings_accounts_description),
                    destination = ::AccountSettingsScreen
                )

                if (IsDeveloper) {
                    SettingsCategory(
                        icon = Icons.Outlined.Code,
                        text = stringResource(Res.strings.settings_development),
                        subtext = stringResource(Res.strings.settings_development_description),
                        destination = ::DeveloperSettingsScreen
                    )
                }

                SettingsCategory(
                    icon = Icons.Outlined.Info,
                    text = stringResource(Res.strings.settings_about),
                    subtext = "${stringResource(Res.strings.app_name)} v$VersionName",
                    destination = ::AboutScreen
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

}