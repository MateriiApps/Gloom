package com.materiapps.gloom.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.materiapps.gloom.domain.manager.AuthManager
import com.materiapps.gloom.domain.repository.GithubAuthRepository
import com.materiapps.gloom.rest.utils.ifSuccessful
import com.materiapps.gloom.ui.screens.auth.LandingScreen
import com.materiapps.gloom.ui.screens.root.RootScreen
import com.materiapps.gloom.ui.theme.GloomTheme
import com.materiapps.gloom.utils.deeplinks.DeepLinkWrapper
import com.materiapps.gloom.utils.deeplinks.addAllRoutes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class GloomActivity : ComponentActivity() {

    private val authRepo: GithubAuthRepository by inject()
    private val auth: AuthManager by inject()
    private lateinit var navigator: Navigator

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            GloomTheme {
                val systemUiController = rememberSystemUiController()
                val surface = MaterialTheme.colorScheme.surface
                auth.refreshAccessToken()

                val defaultScreen = if (auth.isSignedIn)
                    RootScreen()
                else
                    LandingScreen()

                SideEffect {
                    systemUiController.apply {
                        setSystemBarsColor(
                            color = surface,
                            darkIcons = false,
                        )
                        isNavigationBarContrastEnforced = true
                    }
                }

                DeepLinkWrapper { handler ->
                    Navigator(
                        screen = defaultScreen
                    ) {
                        navigator = it
                        SlideTransition(it)
                        handler.addAllRoutes(it)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            if (it.data.toString().startsWith("gloom://oauth")) {
                println(it.data.toString())
                it.data!!.getQueryParameter("code")?.let {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val res = authRepo.getAccessToken(it)
                        res.ifSuccessful { token ->
                            auth.authToken = token.accessToken
                            auth.refreshToken = token.refreshToken
                            navigator.replace(RootScreen())
                        }
                    }
                }
            }
        }
    }

}