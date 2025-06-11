package dev.materii.gloom.ui.screen.settings.component

import androidx.compose.material3.Text
import androidx.compose.runtime.*

// FIXME: Compose introduced a regression that causes an IllegalAccessException when trying to open the dialog, should either wait for the fix or downgrade at some point
@Composable
inline fun <reified E: Enum<E>> SettingsItemChoice(
    label: String,
    title: String = label,
    pref: E,
    enabled: Boolean = true,
    crossinline labelFactory: (E) -> String = { it.toString() },
    crossinline onPrefChange: (E) -> Unit,
) {
    val choiceLabel = labelFactory(pref)
    var opened by remember {
        mutableStateOf(false)
    }

    SettingsItem(
        text = { Text(text = label) },
        secondaryText = { Text(choiceLabel) },
        onClick = { opened = true },
        enabled = enabled
    ) {
        SettingsChoiceDialog(
            visible = opened,
            title = { Text(title) },
            default = pref,
            labelFactory = labelFactory,
            onRequestClose = {
                opened = false
            },
            onChoice = {
                opened = false
                onPrefChange(it)
            }
        )
    }
}