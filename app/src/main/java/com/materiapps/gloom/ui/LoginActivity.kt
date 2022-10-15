package com.materiapps.gloom.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.materiapps.gloom.domain.manager.AuthManager
import com.materiapps.gloom.domain.repository.GithubAuthRepository
import com.materiapps.gloom.rest.utils.ifSuccessful
import com.materiapps.gloom.ui.screens.auth.LandingScreen
import com.materiapps.gloom.ui.theme.GloomTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginActivity : ComponentActivity(), KoinComponent {

    val authRepo: GithubAuthRepository by inject()
    val auth: AuthManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GloomTheme {
                val systemUiController = rememberSystemUiController()
                val surface = MaterialTheme.colorScheme.surface

                SideEffect {
                    systemUiController.apply {
                        setSystemBarsColor(
                            color = surface,
                            darkIcons = false,
                        )
                        isNavigationBarContrastEnforced = true
                    }
                }

                Navigator(LandingScreen())
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
                            Intent(this@LoginActivity, GloomActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(this)
                            }
                            finish()
                        }
                    }
                }
            }
        }
    }

}