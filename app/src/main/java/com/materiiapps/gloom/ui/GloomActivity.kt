package com.materiiapps.gloom.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.materiiapps.gloom.api.repository.GithubAuthRepository
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.utils.fold
import com.materiiapps.gloom.api.utils.ifSuccessful
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.domain.manager.PreferenceManager
import com.materiiapps.gloom.domain.manager.Theme
import com.materiiapps.gloom.ui.screens.auth.LandingScreen
import com.materiiapps.gloom.ui.screens.root.RootScreen
import com.materiiapps.gloom.ui.theme.GloomTheme
import com.materiiapps.gloom.ui.transitions.SlideTransition
import com.materiiapps.gloom.utils.LinkHandler
import com.materiiapps.gloom.utils.LocalLinkHandler
import com.materiiapps.gloom.utils.deeplinks.DeepLinkWrapper
import com.materiiapps.gloom.utils.deeplinks.addAllRoutes
import com.materiiapps.gloom.utils.getOAuthCode
import com.materiiapps.gloom.utils.isOAuthUri
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject

class GloomActivity : ComponentActivity() {

    private val authRepo: GithubAuthRepository by inject()
    private val gqlRepo: GraphQLRepository by inject()
    private val auth: AuthManager by inject()
    private val prefs: PreferenceManager by inject()
    private lateinit var navigator: Navigator

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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            if (it.isOAuthUri()) {
                if(auth.awaitingAuthType == null) return
                it.getOAuthCode()?.let {
                    lifecycleScope.launch {
                        auth.setAuthState(loading = true)
                        val res = authRepo.getAccessToken(it)
                        res.fold(
                            success = { token ->
                                gqlRepo.getAccountId(token.accessToken).ifSuccessful { id ->
                                    auth.addAccount(id, token.accessToken, auth.awaitingAuthType!!)
                                    auth.switchToAccount(id)
                                    navigator.replaceAll(RootScreen())
                                }
                                auth.setAuthState(authType = null, loading = false)
                            },
                            error = { auth.setAuthState(authType = null, loading = false) },
                            failure = { auth.setAuthState(authType = null, loading = false) },
                            empty = { auth.setAuthState(authType = null, loading = false) }
                        )
                    }
                }
            }
        }
    }

}