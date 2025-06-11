package dev.materii.gloom.ui.widget.alert

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun AlertHost(
    content: @Composable (AlertController) -> Unit
) {
    val controller = remember { AlertController() }

    Box(
        modifier = Modifier.fillMaxHeight()
    ) {
        CompositionLocalProvider(
            LocalAlertController provides controller
        ) {
            content(controller)
        }

        val currentAlert = controller.displayedAlert
        var id: String? by remember {
            mutableStateOf(null)
        }

        LaunchedEffect(currentAlert) {
            currentAlert?.let { alert ->
                id = alert.id.toString()
                delay(alert.duration.millis)
                controller.processQueue()
            }
        }

        AlertPopup(
            visible = currentAlert != null,
            title = currentAlert?.title,
            message = currentAlert?.message,
            icon = currentAlert?.icon,
            iconContentDescription = currentAlert?.iconContentDescription,
            position = currentAlert?.position ?: Alert.Position.TOP,
            onClick = currentAlert?.onClick,
            key = id,
            offset = controller.currentOffset,
            onDismissed = {
                controller.processQueue()
            }
        )
    }
}