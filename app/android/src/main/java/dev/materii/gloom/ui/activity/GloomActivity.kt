package dev.materii.gloom.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cafe.adriel.voyager.core.screen.Screen
import dev.materii.gloom.domain.manager.AuthManager
import dev.materii.gloom.ui.App
import dev.materii.gloom.ui.screen.root.RootScreen
import dev.materii.gloom.ui.util.toPx
import dev.materii.gloom.util.LinkHandler
import dev.materii.gloom.util.deeplink.DeepLinkHandler
import org.koin.android.ext.android.inject

open class GloomActivity: ComponentActivity() {

    protected val auth: AuthManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        if (!auth.isSignedIn) {
            val activityIntent = Intent(
                this,
                OAuthActivity::class.java
            )
            startActivity(activityIntent)
            return
        }

        setupDefaultContent()
    }

    protected open fun setupDefaultContent() {
        setupContent(listOf(RootScreen()))
    }

    protected fun setupContent(screens: List<Screen>) {
        setContent {
            val navBarOffset = -((80 + 24).dp.toPx()) // Roughly the height of a navbar

            App(
                screens = screens,
                linkHandler = LinkHandler(LocalContext.current),
                onScreenChange = { screen, alertController ->
                    // Displace bottom alerts when a navbar is present, only temporary
                    if (screen is RootScreen) {
                        alertController.currentOffset = IntOffset(
                            0,
                            navBarOffset
                        )
                    } else {
                        alertController.currentOffset = IntOffset.Companion.Zero
                    }
                },
                onContentChange = {
                    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.Companion.auto(
                            MaterialTheme.colorScheme.scrim.toArgb(),
                            MaterialTheme.colorScheme.scrim.toArgb(),
                            detectDarkMode = { isDark }
                        )
                    )
                },
                onAttach = { nav, _ ->
                    addOnNewIntentListener {
                        nav.push(DeepLinkHandler.handle(it))
                    }
                })
        }
    }

}