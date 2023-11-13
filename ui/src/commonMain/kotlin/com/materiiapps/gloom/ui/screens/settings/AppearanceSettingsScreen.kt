package com.materiiapps.gloom.ui.screens.settings

import androidx.compose.foundation.layout.Column
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.domain.manager.PreferenceManager
import com.materiiapps.gloom.domain.manager.Theme
import com.materiiapps.gloom.ui.components.toolbar.LargeToolbar
import com.materiiapps.gloom.ui.components.settings.SettingsItemChoice
import com.materiiapps.gloom.ui.utils.getString
import com.materiiapps.gloom.ui.viewmodels.settings.AppearanceSettingsViewModel
import com.materiiapps.gloom.utils.Feature
import com.materiiapps.gloom.utils.Features
import dev.icerock.moko.resources.compose.stringResource

class AppearanceSettingsScreen : Screen {

    @Composable
    override fun Content() = Screen()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Screen(
        viewModel: AppearanceSettingsViewModel = getScreenModel()
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
                if (Features.contains(Feature.DYNAMIC_COLOR)) {
                    DynamicColorSetting(viewModel.prefs)
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

@Composable
expect fun DynamicColorSetting(prefs: PreferenceManager)