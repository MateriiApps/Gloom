package com.materiapps.gloom.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.materiapps.gloom.domain.manager.AuthManager
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    val authManager: AuthManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        if (authManager.isSignedIn) {
            authManager.refreshAccessToken()
            Intent(this@MainActivity, GloomActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
            }
        } else {
            Intent(this@MainActivity, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
            }
        }
        finish()

    }
}