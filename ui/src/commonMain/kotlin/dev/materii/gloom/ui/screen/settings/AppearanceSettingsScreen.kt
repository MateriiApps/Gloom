package dev.materii.gloom.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import dev.materii.gloom.Res
import dev.materii.gloom.domain.manager.enums.Theme
import dev.materii.gloom.ui.component.toolbar.LargeToolbar
import dev.materii.gloom.ui.screen.settings.component.AvatarShapeSetting
import dev.materii.gloom.ui.screen.settings.component.SettingsHeader
import dev.materii.gloom.ui.screen.settings.component.SettingsItemChoice
import dev.materii.gloom.ui.screen.settings.component.SettingsSwitch
import dev.materii.gloom.ui.screen.settings.viewmodel.AppearanceSettingsViewModel
import dev.materii.gloom.util.Feature
import dev.materii.gloom.util.Features
import dev.materii.gloom.util.getString
import dev.materii.gloom.util.supportsMonet
import dev.icerock.moko.resources.compose.stringResource

class AppearanceSettingsScreen : Screen {

    @Composable
    override fun Content() = Screen()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Screen(
        viewModel: AppearanceSettingsViewModel = koinScreenModel()
    ) {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        Scaffold(
            topBar = { Toolbar(scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { pv ->
            Column(
                modifier = Modifier
                    .padding(pv)
                    .verticalScroll(rememberScrollState())
            ) {
                SettingsHeader(stringResource(Res.strings.appearance_theme))

                if (dev.materii.gloom.util.Features.contains(Feature.DYNAMIC_COLOR)) {
                    SettingsSwitch(
                        label = stringResource(Res.strings.appearance_monet),
                        secondaryLabel = stringResource(Res.strings.appearance_monet_description),
                        pref = viewModel.prefs.monet,
                        disabled = !dev.materii.gloom.util.supportsMonet
                    ) { viewModel.prefs.monet = it }
                }

                SettingsItemChoice(
                    label = stringResource(Res.strings.appearance_theme),
                    pref = viewModel.prefs.theme,
                    labelFactory = {
                        when (it) {
                            Theme.SYSTEM -> getString(Res.strings.theme_system)
                            Theme.LIGHT -> getString(Res.strings.theme_light)
                            Theme.DARK -> getString(Res.strings.theme_dark)
                        }
                    }
                ) { viewModel.prefs.theme = it }

                SettingsHeader(stringResource(Res.strings.appearance_user_av_shape))
                AvatarShapeSetting(
                    currentShape = viewModel.prefs.userAvatarShape,
                    onShapeUpdate = { viewModel.prefs.userAvatarShape = it },
                    cornerRadius = viewModel.prefs.userAvatarRadius,
                    onCornerRadiusUpdate = { viewModel.prefs.userAvatarRadius = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                SettingsHeader(stringResource(Res.strings.appearance_org_av_shape))
                AvatarShapeSetting(
                    currentShape = viewModel.prefs.orgAvatarShape,
                    onShapeUpdate = { viewModel.prefs.orgAvatarShape = it },
                    cornerRadius = viewModel.prefs.orgAvatarRadius,
                    onCornerRadiusUpdate = { viewModel.prefs.orgAvatarRadius = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Toolbar(
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        LargeToolbar(
            title = stringResource(Res.strings.settings_appearance),
            scrollBehavior = scrollBehavior
        )
    }

}