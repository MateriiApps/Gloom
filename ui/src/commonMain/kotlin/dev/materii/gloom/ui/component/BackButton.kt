package dev.materii.gloom.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.materii.gloom.Res
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun BackButton() {
    val nav = LocalNavigator.current

    if (nav?.canPop == true) {
        IconButton(onClick = { nav.pop() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(Res.strings.action_back))
        }
    }
}