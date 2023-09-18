package com.materiiapps.gloom.ui.widgets.alerts

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AlertController(coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)) :
    CoroutineScope by coroutineScope {

    private val queueLimit = 9

    private val alertQueue = mutableListOf<Alert>()

    var displayedAlert by mutableStateOf<Alert?>(null)
        private set

    fun showText(text: String) = showAlert(title = text, icon = Icons.Outlined.Info)

    fun showAlert(
        title: String? = null,
        message: String? = null,
        icon: ImageVector? = null,
        iconContentDescription: String? = null,
        duration: Alert.Duration = Alert.Duration.SHORT,
        onClick: (() -> Unit)? = null,
    ) {
        val alert = Alert(
            title = title,
            message = message,
            icon = icon,
            iconContentDescription = iconContentDescription,
            duration = duration,
            onClick = onClick
        )

        val canBeDisplayed = alertQueue.isEmpty() && displayedAlert == null
        val queueIsFull = displayedAlert != null && alertQueue.size >= queueLimit

        if (queueIsFull) return

        if (canBeDisplayed)
            displayedAlert = alert
        else
            alertQueue.add(alert)
    }

    internal fun processQueue() {
        launch {
            displayedAlert = null
            delay(600)
            alertQueue.firstOrNull()?.let {
                alertQueue.removeFirst()
                displayedAlert = it
            }
        }
    }
}

val LocalAlertController =
    staticCompositionLocalOf<AlertController> { error("No AlertController present") }

@Stable
data class Alert(
    val title: String? = null,
    val message: String? = null,
    val icon: ImageVector? = null,
    val iconContentDescription: String? = null,
    val duration: Duration = Duration.SHORT,
    val onClick: (() -> Unit)? = null,
    val id: Uuid = uuid4()
) {

    @Stable
    enum class Duration(val millis: Long) {
        SHORT(millis = 4000),
        MEDIUM(millis = 7000),
        LONG(millis = 10_000)
    }

}