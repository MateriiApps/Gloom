package com.materiiapps.gloom.ui.screen.profile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.materiiapps.gloom.Res
import dev.icerock.moko.resources.compose.stringResource

class ProfileTab : Tab, ProfileScreen() {
    override val options: TabOptions
        @Composable get() {
            val navigator = LocalTabNavigator.current
            val selected = navigator.current == this
            return TabOptions(
                0u,
                stringResource(Res.strings.navigation_profile),
                rememberVectorPainter(if (selected) Icons.Filled.Person else Icons.Outlined.Person)
            )
        }
}