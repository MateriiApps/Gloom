package com.materiiapps.gloom.ui

import android.content.Context
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
import cafe.adriel.voyager.navigator.Navigator
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.ui.screen.auth.LandingScreen
import com.materiiapps.gloom.ui.screen.root.RootScreen
import com.materiiapps.gloom.ui.util.toPx
import com.materiiapps.gloom.ui.viewmodel.main.MainViewModel
import com.materiiapps.gloom.util.LinkHandler
import com.materiiapps.gloom.util.deeplink.DeepLinkWrapper
import com.materiiapps.gloom.util.deeplink.addAllRoutes
import com.materiiapps.gloom.util.getOAuthCode
import com.materiiapps.gloom.util.isOAuthUri
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GloomActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val auth: AuthManager by inject()

    private lateinit var navigator: Navigator
    private var isLastIntentOauth: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        getKoin().declare<Context>(this@GloomActivity)

        setContent {
            val navBarOffset = -((80 + 24).dp.toPx()) // Roughly the height of a navbar
            val defaultScreen = if (auth.isSignedIn) RootScreen() else LandingScreen()

            DeepLinkWrapper { handler ->
                App(
                    startingScreen = defaultScreen,
                    linkHandler = LinkHandler(LocalContext.current),
                    onScreenChange = { screen, alertController ->
                        // Displace bottom alerts when a navbar is present, only temporary
                        if (screen is RootScreen)
                            alertController.currentOffset = IntOffset(0, navBarOffset)
                        else
                            alertController.currentOffset = IntOffset.Zero
                    },
                    onContentChange = {
                        val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
                        enableEdgeToEdge(
                            statusBarStyle = SystemBarStyle.auto(
                                MaterialTheme.colorScheme.scrim.toArgb(),
                                MaterialTheme.colorScheme.scrim.toArgb(),
                                detectDarkMode = { isDark }
                            )
                        )
                    },
                    onAttach = { nav, _ ->
                        navigator = nav
                        handler.addAllRoutes(nav, auth)
                    }
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        isLastIntentOauth = intent.isOAuthUri()
        if (intent.isOAuthUri()) {
            if (viewModel.authManager.awaitingAuthType == null) return
            intent.getOAuthCode()?.let { code ->
                viewModel.loginWithOAuthCode(code) {
                    navigator.replaceAll(RootScreen())
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isLastIntentOauth) {
            auth.setAuthState(authType = null)
        }
    }

}