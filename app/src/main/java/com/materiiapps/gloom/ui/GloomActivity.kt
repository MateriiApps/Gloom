package com.materiiapps.gloom.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.domain.manager.PreferenceManager
import com.materiiapps.gloom.domain.manager.Theme
import com.materiiapps.gloom.ui.screens.auth.LandingScreen
import com.materiiapps.gloom.ui.screens.root.RootScreen
import com.materiiapps.gloom.ui.theme.GloomTheme
import com.materiiapps.gloom.ui.transitions.SlideTransition
import com.materiiapps.gloom.ui.utils.toPx
import com.materiiapps.gloom.ui.viewmodels.main.MainViewModel
import com.materiiapps.gloom.ui.widgets.alerts.AlertHost
import com.materiiapps.gloom.utils.LinkHandler
import com.materiiapps.gloom.utils.LocalLinkHandler
import com.materiiapps.gloom.utils.deeplinks.DeepLinkWrapper
import com.materiiapps.gloom.utils.deeplinks.addAllRoutes
import com.materiiapps.gloom.utils.getOAuthCode
import com.materiiapps.gloom.utils.isOAuthUri
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GloomActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val auth: AuthManager by inject()
    private val prefs: PreferenceManager by inject()

    private lateinit var navigator: Navigator
    private var isLastIntentOauth: Boolean = intent?.isOAuthUri() ?: false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        getKoin().declare<Context>(this@GloomActivity)

        setContent {
            val isDark = when (prefs.theme) {
                Theme.SYSTEM -> isSystemInDarkTheme()
                Theme.LIGHT -> false
                Theme.DARK -> true
            }

            GloomTheme(isDark, prefs.monet) {
                val systemUiController = rememberSystemUiController()
                val navNarOffset = -((80 + 24).dp.toPx())
                val defaultScreen = if (auth.isSignedIn)
                    RootScreen()
                else
                    LandingScreen()

                SideEffect {
                    systemUiController.apply {
                        setSystemBarsColor(
                            color = Color.Transparent,
                            darkIcons = !isDark,
                        )
                        isNavigationBarContrastEnforced = true
                    }
                }

                DeepLinkWrapper { handler ->
                    AlertHost { alertController ->
                        Navigator(
                            screen = defaultScreen,
                            disposeBehavior = NavigatorDisposeBehavior(
                                disposeNestedNavigators = false,
                                disposeSteps = true
                            )
                        ) {
                            LaunchedEffect(it.lastItem) {
                                if(it.lastItem is RootScreen)
                                    alertController.currentOffset = IntOffset(0, navNarOffset)
                                else
                                    alertController.currentOffset = IntOffset.Zero
                            }

                            CompositionLocalProvider(
                                LocalLinkHandler provides LinkHandler(LocalContext.current)
                            ) {
                                navigator = it
                                SlideTransition(it)
                                handler.addAllRoutes(it, auth)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        isLastIntentOauth = intent?.isOAuthUri() ?: false
        intent?.let {
            if (it.isOAuthUri()) {
                if (viewModel.authManager.awaitingAuthType == null) return
                it.getOAuthCode()?.let { code ->
                    viewModel.loginWithOAuthCode(code) {
                        navigator.replaceAll(RootScreen())
                    }
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