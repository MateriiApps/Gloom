package com.materiiapps.gloom.ui.component.filter

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlin.enums.enumEntries

/**
 * An [InputChip] used to make a single selection from a set
 * of options using an enum.
 *
 * Clicking the chip will open a dropdown containing all the entries
 * contained in the provided enum [E], once the user selects an item [onChoiceSelected]
 * will be called and the dropdown dismissed.
 *
 * @param E The enum used to supply the choices
 *
 * @param defaultValue The default value, if the current choice differs from this value then
 * the chip will be marked as selected
 * @param currentValue The currently chosen item
 * @param onChoiceSelected Called when the user makes their choice
 * @param modifier The [Modifier] for this chip
 * @param label Factory function used to get a localized label for the enum entry, defaults to the enum entries name.
 * It is not reccommended to override the text style or add anything more than a [Text] component.
 */
@Composable
inline fun <reified E: Enum<E>> ChoiceInputChip(
    defaultValue: E,
    currentValue: E,
    crossinline onChoiceSelected: (E) -> Unit,
    modifier: Modifier = Modifier,
    crossinline label: @Composable (E) -> Unit = { Text(it.name) }
) {
    var dropdownVisible by remember { mutableStateOf(false) }

    InputChip(
        onClick = { dropdownVisible = true },
        selected = currentValue != defaultValue,
        label = { label(currentValue) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.ExpandMore,
                contentDescription = null
            )
        },
        modifier = modifier
    )

    Box {
        DropdownMenu(
            expanded = dropdownVisible,
            onDismissRequest = { dropdownVisible = false },
            offset = DpOffset(0.dp, 24.dp)
        ) {
            enumEntries<E>().forEach { entry ->
                DropdownMenuItem(
                    text = { label(entry) },
                    onClick = {
                        dropdownVisible = false
                        if (currentValue != entry) onChoiceSelected(entry)
                    }
                )
            }
        }
    }
}