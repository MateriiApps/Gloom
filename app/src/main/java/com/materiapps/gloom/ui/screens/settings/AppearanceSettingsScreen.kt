package com.materiapps.gloom.ui.screens.settings

import android.os.Build
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.materiapps.gloom.R
import com.materiapps.gloom.ui.components.LargeToolbar
import com.materiapps.gloom.ui.components.settings.SettingsItemChoice
import com.materiapps.gloom.ui.components.settings.SettingsSwitch
import com.materiapps.gloom.ui.viewmodels.settings.AppearanceSettingsViewModel

class AppearanceSettingsScreen : Screen {

    @Composable
    override fun Content() = Screen()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Screen(
        viewModel: AppearanceSettingsViewModel = getScreenModel()
    ) {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val ctx = LocalContext.current

        Scaffold(
            topBar = { Toolbar(scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { pv ->
            Column(
                modifier = Modifier
                    .padding(pv)
                    .verticalScroll(rememberScrollState())
            ) {

                SettingsSwitch(
                    label = stringResource(R.string.appearance_monet),
                    secondaryLabel = stringResource(R.string.appearance_monet_description),
                    pref = viewModel.prefs.monet,
                    disabled = Build.VERSION.SDK_INT < Build.VERSION_CODES.S
                ) { viewModel.prefs.monet = it }

                SettingsItemChoice(
                    label = stringResource(R.string.appearance_theme),
                    pref = viewModel.prefs.theme,
                    labelFactory = { ctx.getString(it.label) }
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
            title = stringResource(R.string.settings_appearance),
            scrollBehavior = scrollBehavior
        )
    }

}