package com.materiapps.gloom.ui.screens.explore

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

class ExploreScreen : Tab {
    override val options: TabOptions
        @Composable get() {
            val navigator = LocalTabNavigator.current
            val selected = navigator.current == this
            return TabOptions(
                0u,
                "Explore",
                rememberVectorPainter(if (selected) Icons.Filled.Explore else Icons.Outlined.Explore)
            )
        }

    @Composable
    override fun Content() = Screen()

    @Composable
    private fun Screen() {

    }
}