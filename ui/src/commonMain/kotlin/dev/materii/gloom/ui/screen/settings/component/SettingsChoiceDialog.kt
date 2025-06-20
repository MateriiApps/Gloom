package dev.materii.gloom.ui.screen.settings.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.ui.component.EnumRadioController

@Composable
inline fun <reified E: Enum<E>> SettingsChoiceDialog(
    default: E,
    noinline title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    crossinline labelFactory: (E) -> String = { it.toString() },
    noinline onRequestClose: () -> Unit = {},
    crossinline description: @Composable () -> Unit = {},
    noinline onChoice: (E) -> Unit = {},
    visible: Boolean = false
) {

    var choice by remember { mutableStateOf(default) }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(),
        exit = slideOutVertically(),
        modifier = modifier
    ) {
        AlertDialog(
            onDismissRequest = { onRequestClose() },
            title = title,
            text = {
                description()
                EnumRadioController(
                    default = default,
                    labelFactory = labelFactory,
                    onChoiceSelect = { choice = it }
                )
            },
            confirmButton = {
                FilledTonalButton(onClick = { onChoice(choice) }) {
                    Text(text = stringResource(Res.strings.action_confirm))
                }
            }
        )
    }

}