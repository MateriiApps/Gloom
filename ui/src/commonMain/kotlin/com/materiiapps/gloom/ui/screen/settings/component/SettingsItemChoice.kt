package com.materiiapps.gloom.ui.screen.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
inline fun <reified E : Enum<E>> SettingsItemChoice(
    label: String,
    title: String = label,
    disabled: Boolean = false,
    pref: E,
    crossinline labelFactory: (E) -> String = { it.toString() },
    crossinline onPrefChange: (E) -> Unit,
) {
    val choiceLabel = labelFactory(pref)
    val opened = remember {
        mutableStateOf(false)
    }

    SettingItem(
        modifier = Modifier.clickable { opened.value = true },
        text = { Text(text = label) },
    ) {
        SettingsChoiceDialog(
            visible = opened.value,
            title = { Text(title) },
            default = pref,
            labelFactory = labelFactory,
            onRequestClose = {
                opened.value = false
            },
            onChoice = {
                opened.value = false
                onPrefChange(it)
            }
        )
        FilledTonalButton(onClick = { opened.value = true }, enabled = !disabled) {
            Text(choiceLabel)
        }
    }
}