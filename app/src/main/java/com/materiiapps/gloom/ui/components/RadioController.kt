package com.materiiapps.gloom.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
inline fun <reified E : Enum<E>> EnumRadioController(
    default: E,
    labelFactory: (E) -> String = { it.toString() },
    crossinline onChoiceSelected: (E) -> Unit
) {
    var choice by remember { mutableStateOf(default) }

    Column {
        enumValues<E>().forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        choice = it
                        onChoiceSelected(it)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(labelFactory(it))
                Spacer(Modifier.weight(1f))
                RadioButton(
                    selected = it == choice,
                    onClick = {
                        choice = it
                        onChoiceSelected(it)
                    })
            }
        }
    }

}