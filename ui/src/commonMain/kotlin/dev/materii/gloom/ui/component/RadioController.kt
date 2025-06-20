package dev.materii.gloom.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
@Suppress("LambdaParameterEventTrailing")
inline fun <reified E: Enum<E>> EnumRadioController(
    default: E,
    modifier: Modifier = Modifier,
    labelFactory: (E) -> String = { it.toString() },
    crossinline onChoiceSelect: (E) -> Unit
) {
    var choice by remember { mutableStateOf(default) }

    Column(
        modifier = modifier
    ) {
        enumValues<E>().forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        choice = it
                        onChoiceSelect(it)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(labelFactory(it))
                Spacer(Modifier.weight(1f))
                RadioButton(
                    selected = it == choice,
                    onClick = {
                        choice = it
                        onChoiceSelect(it)
                    })
            }
        }
    }

}