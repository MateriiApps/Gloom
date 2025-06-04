package dev.materii.gloom.ui.activity

import android.widget.Toast
import dev.materii.gloom.util.deeplink.DeepLinkHandler

class DeepLinkActivity : GloomActivity() {
    override fun setupDefaultContent() {
        val backstack = DeepLinkHandler.handle(intent).ifEmpty {
            finish()
            Toast.makeText(this, "Unimplemented route", Toast.LENGTH_SHORT).show()
            return
        }
        setupContent(backstack)
    }
}