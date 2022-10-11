package com.materiapps.gloom.ui.screens.notifications

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

class NotificationsScreen : Tab {
    override val options: TabOptions
        @Composable get() {
            val navigator = LocalTabNavigator.current
            val selected = navigator.current == this
            return TabOptions(
                0u,
                "Notifications",
                rememberVectorPainter(if (selected) Icons.Filled.Notifications else Icons.Outlined.Notifications)
            )
        }

    @Composable
    override fun Content() = Screen()

    @Composable
    private fun Screen() {

    }
}