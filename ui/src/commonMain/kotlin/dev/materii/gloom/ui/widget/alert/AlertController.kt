package dev.materii.gloom.ui.widget.alert

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.IntOffset
import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AlertController(coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)):
    CoroutineScope by coroutineScope {

    private val queueLimit = 9

    private val alertQueue = mutableListOf<Alert>()

    var displayedAlert by mutableStateOf<Alert?>(null)
        private set

    var currentOffset by mutableStateOf<IntOffset>(IntOffset.Zero)

    fun showText(
        text: String,
        icon: ImageVector = Icons.Outlined.Info,
        position: Alert.Position = Alert.Position.BOTTOM
    ) = showAlert(title = text, icon = icon, position = position)

    fun showAlert(
        title: String? = null,
        message: String? = null,
        icon: ImageVector? = null,
        iconContentDescription: String? = null,
        duration: Alert.Duration = Alert.Duration.SHORT,
        position: Alert.Position = Alert.Position.TOP,
        onClick: (() -> Unit)? = null,
    ) {
        val alert = Alert(
            title = title,
            message = message,
            icon = icon,
            iconContentDescription = iconContentDescription,
            duration = duration,
            position = position,
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
    val position: Position = Position.TOP,
    val onClick: (() -> Unit)? = null,
    val id: Uuid = uuid4()
) {

    @Stable
    enum class Duration(val millis: Long) {

        SHORT(millis = 4000),
        MEDIUM(millis = 7000),
        LONG(millis = 10_000)
    }

    @Stable
    enum class Position {

        TOP,
        BOTTOM
    }

}