package dev.materii.gloom.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.DesignServices
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.ui.component.toolbar.LargeToolbar
import dev.materii.gloom.ui.screen.auth.LandingScreen
import dev.materii.gloom.ui.screen.settings.about.AboutScreen
import dev.materii.gloom.ui.screen.settings.component.SettingsCategory
import dev.materii.gloom.ui.screen.settings.component.SettingsGroup
import dev.materii.gloom.ui.screen.settings.component.account.SignOutDialog
import dev.materii.gloom.ui.screen.settings.developer.DeveloperSettingsScreen
import dev.materii.gloom.ui.screen.settings.viewmodel.SettingsViewModel
import dev.materii.gloom.util.Feature
import dev.materii.gloom.util.IsDeveloper
import dev.materii.gloom.util.VersionName

class SettingsScreen: Screen {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val viewModel: SettingsViewModel = koinScreenModel()
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
            topBar = { Toolbar(scrollBehavior, onSignOutClick = viewModel::openSignOutDialog) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier)
                SettingsGroup {
                    SettingsCategory(
                        icon = Icons.Outlined.Palette,
                        text = stringResource(Res.strings.settings_appearance),
                        subtext = stringResource(Res.strings.settings_appearance_description),
                        destination = ::AppearanceSettingsScreen
                    )

                    if (dev.materii.gloom.util.Features.contains(Feature.CHANGE_ICON)) {
                        SettingsCategory(
                            icon = Icons.Outlined.DesignServices,
                            text = stringResource(Res.strings.settings_app_icon),
                            subtext = stringResource(Res.strings.settings_app_icon_description),
                            destination = ::AppIconsSettingsScreen
                        )
                    }
                }

                SettingsGroup {
                    SettingsCategory(
                        icon = Icons.Outlined.AccountCircle,
                        text = stringResource(Res.strings.settings_accounts),
                        subtext = stringResource(Res.strings.settings_accounts_description),
                        destination = ::AccountSettingsScreen
                    )
                }

                SettingsGroup {
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
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Toolbar(
        scrollBehavior: TopAppBarScrollBehavior,
        onSignOutClick: () -> Unit
    ) {
        LargeToolbar(
            title = stringResource(Res.strings.navigation_settings),
            scrollBehavior = scrollBehavior,
            actions = {
                IconButton(
                    onClick = onSignOutClick,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Logout,
                        contentDescription = stringResource(Res.strings.action_sign_out)
                    )
                }
            }
        )
    }

}