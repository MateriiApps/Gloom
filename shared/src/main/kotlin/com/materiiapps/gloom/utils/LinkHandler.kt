package com.materiiapps.gloom.utils

import android.content.Context
import android.util.Log
import androidx.compose.runtime.compositionLocalOf
import androidx.core.net.toUri

class LinkHandler(private val ctx: Context) {

    fun openLink(link: String, forceCustomTab: Boolean = false) {
        try {
            ctx.openLink(link.toUri(), forceCustomTab)
        } catch (e: Throwable) {
            Log.e("LinkHandler", "Failed to open link", e)
        }
    }

}

val LocalLinkHandler = compositionLocalOf<LinkHandler> { error("No link handler set") }