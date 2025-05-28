package dev.materii.gloom.ui.screen.settings.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.materii.gloom.ui.util.navigate

@Composable
fun SettingsCategory(
    icon: ImageVector,
    text: String,
    subtext: String,
    modifier: Modifier = Modifier,
    destination: (() -> Screen)? = null
) {
    val screen = destination?.invoke()
    val nav = LocalNavigator.currentOrThrow

    SettingsItem(
        text = { Text(text) },
        secondaryText = { Text(subtext) },
        leading = { SettingsCategoryIcon(icon) },
        onClick = { if (screen != null) nav.navigate(screen) },
        modifier = modifier
    )
}