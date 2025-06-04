package dev.materii.gloom.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import dev.materii.gloom.domain.manager.PreferenceManager
import dev.materii.gloom.domain.manager.enums.Theme
import dev.materii.gloom.ui.activity.viewmodel.OAuthViewModel
import dev.materii.gloom.ui.screen.auth.LandingScreen
import dev.materii.gloom.ui.theme.GloomTheme
import dev.materii.gloom.util.LinkHandler
import dev.materii.gloom.util.LocalLinkHandler
import dev.materii.gloom.util.getOAuthCode
import dev.materii.gloom.util.isOAuthUri
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OAuthActivity : ComponentActivity() {
    private val viewModel: OAuthViewModel by viewModel()
    private val prefs: PreferenceManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linkHandler = LinkHandler(this)

        setContent {
            val isDark = when (prefs.theme) {
                Theme.SYSTEM -> isSystemInDarkTheme()
                Theme.LIGHT -> false
                Theme.DARK -> true
            }

            GloomTheme(isDark, prefs.monet) {
                CompositionLocalProvider(LocalLinkHandler provides linkHandler) {
                    LandingScreen().Content()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.isOAuthUri()) {
            if (viewModel.authManager.awaitingAuthType == null) return
            intent.getOAuthCode()?.let { code ->
                viewModel.loginWithOAuthCode(code) {
                    val activityIntent = Intent(this, GloomActivity::class.java)
                    startActivity(activityIntent)
                    finish()
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishAffinity()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }
}