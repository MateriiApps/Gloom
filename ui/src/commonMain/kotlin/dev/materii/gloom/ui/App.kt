package dev.materii.gloom.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import dev.materii.gloom.domain.manager.PreferenceManager
import dev.materii.gloom.domain.manager.enums.Theme
import dev.materii.gloom.ui.theme.GloomTheme
import dev.materii.gloom.ui.transition.SlideTransition
import dev.materii.gloom.ui.widget.alert.AlertController
import dev.materii.gloom.ui.widget.alert.AlertHost
import org.koin.compose.koinInject

/**
 * Platform-independent entry point. Sets up theming, alerts, and navigation.
 *
 * @param screens The screens to start with.
 * @param linkHandler Handles opening links.
 * @param onScreenChange Callback for when a new screen is navigated to.
 * @param onContentChange Called when content is going through a transition
 * @param onAttach Called after the navigator is set up, used to initialize any platform-specific functionality
 */
@OptIn(InternalVoyagerApi::class)
@Composable
fun App(
    screens: List<Screen>,
    linkHandler: dev.materii.gloom.util.LinkHandler,
    onScreenChange: (Screen, AlertController) -> Unit = { _, _, -> },
    onContentChange: @Composable () -> Unit = {},
    onAttach: (Navigator, AlertController) -> Unit = { _, _, -> },
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
                screens = screens,
                disposeBehavior = NavigatorDisposeBehavior(
                    disposeNestedNavigators = false,
                    disposeSteps = true
                ),
            ) { navigator ->
                LaunchedEffect(navigator.lastItem) {
                    onScreenChange(navigator.lastItem, alertController)
                }

                LaunchedEffect(Unit) {
                    onAttach(navigator, alertController)
                }

                CompositionLocalProvider(
                    dev.materii.gloom.util.LocalLinkHandler provides linkHandler
                ) {
                    SlideTransition(navigator) { screen ->
                        screen.Content()
                        onContentChange()
                    }
                }
            }
        }
    }
}