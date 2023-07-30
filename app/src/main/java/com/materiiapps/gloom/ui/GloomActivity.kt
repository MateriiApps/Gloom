package com.materiiapps.gloom.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.materiiapps.gloom.api.repository.GithubAuthRepository
import com.materiiapps.gloom.api.utils.ifSuccessful
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.domain.manager.PreferenceManager
import com.materiiapps.gloom.domain.manager.Theme
import com.materiiapps.gloom.ui.screens.auth.LandingScreen
import com.materiiapps.gloom.ui.screens.root.RootScreen
import com.materiiapps.gloom.ui.theme.GloomTheme
import com.materiiapps.gloom.utils.deeplinks.DeepLinkWrapper
import com.materiiapps.gloom.utils.deeplinks.addAllRoutes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class GloomActivity : ComponentActivity() {

    private val authRepo: GithubAuthRepository by inject()
    private val auth: AuthManager by inject()
    private val prefs: PreferenceManager by inject()
    private lateinit var navigator: Navigator

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isDark = when (prefs.theme) {
                Theme.SYSTEM -> isSystemInDarkTheme()
                Theme.LIGHT -> false
                Theme.DARK -> true
            }

            GloomTheme(isDark, prefs.monet) {
                val systemUiController = rememberSystemUiController()
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
                    Navigator(
                        screen = defaultScreen,
                        disposeBehavior = NavigatorDisposeBehavior(
                            disposeNestedNavigators = false,
                            disposeSteps = true
                        )
                    ) {
                        navigator = it
                        SlideTransition(it)
                        handler.addAllRoutes(it, auth)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            if (it.data.toString().startsWith("github://com.github.android/oauth")) {
                println(it.data.toString())
                it.data!!.getQueryParameter("code")?.let {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val res = authRepo.getAccessToken(it)
                        res.ifSuccessful { token ->
                            auth.authToken = token.accessToken
                            navigator.replace(RootScreen())
                        }
                    }
                }
            }
        }
    }

}