package dev.materii.gloom.ui.screen.settings.component

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

// FIXME: Compose introduced a regression that causes an IllegalAccessException when trying to open the dialog, should either wait for the fix or downgrade at some point
@Composable
inline fun <reified E: Enum<E>> SettingsItemChoice(
    label: String,
    pref: E,
    crossinline onPrefChange: (E) -> Unit,
    modifier: Modifier = Modifier,
    title: String = label,
    crossinline labelFactory: (E) -> String = { it.toString() },
    enabled: Boolean = true
) {
    val choiceLabel = labelFactory(pref)
    var opened by remember {
        mutableStateOf(false)
    }

    SettingsItem(
        text = { Text(text = label) },
        secondaryText = { Text(choiceLabel) },
        onClick = { opened = true },
        enabled = enabled,
        modifier = modifier
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