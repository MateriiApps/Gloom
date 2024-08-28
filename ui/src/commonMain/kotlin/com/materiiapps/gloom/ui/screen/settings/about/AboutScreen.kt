package com.materiiapps.gloom.ui.screen.settings.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import cafe.adriel.voyager.core.screen.Screen
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.screen.settings.component.SettingsCategory
import com.materiiapps.gloom.ui.components.toolbar.LargeToolbar
import dev.icerock.moko.resources.compose.stringResource

class AboutScreen : Screen {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        Scaffold(
            topBar = { Toolbar(scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { pv ->
            Column(
                modifier = Modifier.padding(pv)
            ) {
                SettingsCategory(
                    icon = Icons.Outlined.Book,
                    text = stringResource(Res.strings.settings_libraries),
                    subtext = stringResource(Res.strings.settings_libraries_description),
                    destination = ::LibrariesScreen
                )
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Toolbar(
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        LargeToolbar(
            title = stringResource(Res.strings.settings_about),
            scrollBehavior = scrollBehavior
        )
    }

}