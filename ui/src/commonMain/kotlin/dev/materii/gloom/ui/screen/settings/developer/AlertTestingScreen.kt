package dev.materii.gloom.ui.screen.settings.developer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.ui.component.toolbar.LargeToolbar
import dev.materii.gloom.ui.screen.settings.component.SettingsButton
import dev.materii.gloom.ui.screen.settings.component.SettingsGroup
import dev.materii.gloom.ui.screen.settings.component.SettingsItemChoice
import dev.materii.gloom.ui.screen.settings.component.SettingsSwitch
import dev.materii.gloom.ui.widget.alert.Alert
import dev.materii.gloom.ui.widget.alert.LocalAlertController

class AlertTestingScreen: Screen {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val alertController = LocalAlertController.current
        var title by remember {
            mutableStateOf("Some basic title")
        }
        var message by remember {
            mutableStateOf("Some very basic message text")
        }
        var showIcon by remember {
            mutableStateOf(false)
        }
        var duration by remember {
            mutableStateOf(Alert.Duration.SHORT)
        }
        var position by remember {
            mutableStateOf(Alert.Position.TOP)
        }

        Scaffold(
            topBar = { Toolbar(scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { pv ->
            Column(
                modifier = Modifier
                    .padding(pv)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier.padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        label = {
                            Text(stringResource(Res.strings.dev_alert_title))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Box(
                    modifier = Modifier.padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = message,
                        onValueChange = {
                            message = it
                        },
                        label = {
                            Text(stringResource(Res.strings.dev_alert_message))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                SettingsGroup(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    SettingsSwitch(
                        label = stringResource(Res.strings.dev_alert_show_icon),
                        pref = showIcon,
                        onPrefChange = { showIcon = it }
                    )

                    SettingsItemChoice(
                        label = stringResource(Res.strings.dev_alert_duration),
                        pref = duration,
                        onPrefChange = { duration = it }
                    )

                    SettingsItemChoice(
                        label = "Position",
                        pref = position,
                        onPrefChange = { position = it }
                    )
                }

                SettingsButton(
                    label = stringResource(Res.strings.dev_alert_action_show_alert),
                    onClick = {
                        alertController.showAlert(
                            title = title.ifBlank { null },
                            message = message.ifBlank { null },
                            icon = if (showIcon) Icons.Outlined.Info else null,
                            duration = duration,
                            position = position
                        )
                    }
                )

                SettingsButton(
                    label = stringResource(Res.strings.dev_alert_action_fill_queue),
                    outlined = true,
                    onClick = {
                        for (i in 1..10) {
                            alertController.showAlert(
                                title = title.ifBlank { null },
                                message = message.ifBlank { null },
                                icon = if (showIcon) Icons.Outlined.Info else null,
                                duration = duration,
                                position = position
                            )
                        }
                    }
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
            title = stringResource(Res.strings.dev_alert_testing),
            scrollBehavior = scrollBehavior
        )
    }

}