package dev.materii.gloom.util

import android.content.Context
import android.util.Log
import androidx.compose.runtime.compositionLocalOf
import androidx.core.net.toUri

actual class LinkHandler(private val ctx: Context) {

    actual fun openLink(link: String, forceCustomTab: Boolean) {
        try {
            ctx.openLink(link.toUri(), forceCustomTab)
        } catch (e: Throwable) {
            Log.e("LinkHandler", "Failed to open link", e)
        }
    }

}

actual val LocalLinkHandler = compositionLocalOf<dev.materii.gloom.util.LinkHandler> { error("No link handler set") }