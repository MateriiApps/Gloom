package com.materiiapps.gloom.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import com.materiiapps.gloom.domain.manager.PreferenceManager
import com.materiiapps.gloom.domain.manager.Theme
import com.materiiapps.gloom.ui.theme.GloomTheme
import com.materiiapps.gloom.ui.transitions.SlideTransition
import com.materiiapps.gloom.ui.widgets.alerts.AlertController
import com.materiiapps.gloom.ui.widgets.alerts.AlertHost
import com.materiiapps.gloom.utils.LinkHandler
import com.materiiapps.gloom.utils.LocalLinkHandler
import org.koin.compose.koinInject

/**
 * Platform-independent entry point. Sets up theming, alerts, and navigation.
 *
 * @param startingScreen The screen to start the app on.
 * @param linkHandler Handles opening links.
 * @param onScreenChange Callback for when a new screen is navigated to.
 * @param onAttach Called after the navigator is set up, used to initialize any platform-specific functionality
 */
@Composable
fun App(
    startingScreen: Screen,
    linkHandler: LinkHandler,
    onScreenChange: (Screen, AlertController) -> Unit = { _, _, -> },
    onAttach: @Composable (Navigator, AlertController) -> Unit = { _, _, -> },
) {
    val prefs: PreferenceManager = koinInject()

    val isDark = when (prefs.theme) {
        Theme.SYSTEM -> isSystemInDarkTheme()
        Theme.LIGHT -> false
        Theme.DARK -> true
    }

    GloomTheme(isDark, prefs.monet) {
        AlertHost { alertController ->
            Navigator(
                screen = startingScreen,
                disposeBehavior = NavigatorDisposeBehavior(
                    disposeNestedNavigators = false,
                    disposeSteps = true
                )
            ) { navigator ->
                LaunchedEffect(navigator.lastItem) {
                    onScreenChange(navigator.lastItem, alertController)
                }

                CompositionLocalProvider(
                    LocalLinkHandler provides linkHandler
                ) {
                    SlideTransition(navigator) { screen ->
                        screen.Content()
                        onAttach(navigator, alertController)
                    }
                }
            }
        }
    }
}