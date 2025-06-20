package dev.materii.gloom.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.domain.manager.enums.Theme
import dev.materii.gloom.ui.component.toolbar.LargeToolbar
import dev.materii.gloom.ui.screen.settings.component.*
import dev.materii.gloom.ui.screen.settings.viewmodel.AppearanceSettingsViewModel
import dev.materii.gloom.util.Feature
import dev.materii.gloom.util.getString

class AppearanceSettingsScreen: Screen {

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
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(pv)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                SettingsHeader(stringResource(Res.strings.appearance_theme))
                SettingsGroup {
                    if (dev.materii.gloom.util.Features.contains(Feature.DYNAMIC_COLOR)) {
                        SettingsSwitch(
                            label = stringResource(Res.strings.appearance_monet),
                            secondaryLabel = stringResource(Res.strings.appearance_monet_description),
                            pref = viewModel.prefs.monet,
                            onPrefChange = { viewModel.prefs.monet = it },
                            enabled = dev.materii.gloom.util.supportsMonet
                        )
                    }

                    SettingsItemChoice(
                        label = stringResource(Res.strings.appearance_theme),
                        pref = viewModel.prefs.theme,
                        onPrefChange = { viewModel.prefs.theme = it },
                        labelFactory = {
                            when (it) {
                                Theme.SYSTEM -> getString(Res.strings.theme_system)
                                Theme.LIGHT  -> getString(Res.strings.theme_light)
                                Theme.DARK   -> getString(Res.strings.theme_dark)
                            }
                        }
                    )
                }

                SettingsHeader(stringResource(Res.strings.appearance_av_shape))
                SettingsGroup {
                    AvatarShapeSetting(
                        text = { Text(stringResource(Res.strings.noun_user)) },
                        currentShape = viewModel.prefs.userAvatarShape,
                        onShapeUpdate = { viewModel.prefs.userAvatarShape = it },
                        cornerRadius = viewModel.prefs.userAvatarRadius,
                        onCornerRadiusUpdate = { viewModel.prefs.userAvatarRadius = it }
                    )

                    AvatarShapeSetting(
                        text = { Text(stringResource(Res.strings.noun_org)) },
                        currentShape = viewModel.prefs.orgAvatarShape,
                        onShapeUpdate = { viewModel.prefs.orgAvatarShape = it },
                        cornerRadius = viewModel.prefs.orgAvatarRadius,
                        onCornerRadiusUpdate = { viewModel.prefs.orgAvatarRadius = it }
                    )
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
            title = stringResource(Res.strings.settings_appearance),
            scrollBehavior = scrollBehavior
        )
    }

}