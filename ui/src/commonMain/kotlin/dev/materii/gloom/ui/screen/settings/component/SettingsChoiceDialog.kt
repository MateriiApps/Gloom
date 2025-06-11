package dev.materii.gloom.ui.screen.settings.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.ui.component.EnumRadioController

@Composable
inline fun <reified E: Enum<E>> SettingsChoiceDialog(
    visible: Boolean = false,
    default: E,
    noinline title: @Composable () -> Unit,
    crossinline labelFactory: (E) -> String = { it.toString() },
    noinline onRequestClose: () -> Unit = {},
    crossinline description: @Composable () -> Unit = {},
    noinline onChoice: (E) -> Unit = {},
) {

    var choice by remember { mutableStateOf(default) }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        AlertDialog(
            onDismissRequest = { onRequestClose() },
            title = title,
            text = {
                description()
                EnumRadioController(
                    default,
                    labelFactory
                ) { choice = it }
            },
            confirmButton = {
                FilledTonalButton(onClick = { onChoice(choice) }) {
                    Text(text = stringResource(Res.strings.action_confirm))
                }
            }
        )
    }

}