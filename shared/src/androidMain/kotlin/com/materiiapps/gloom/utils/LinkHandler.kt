package com.materiiapps.gloom.utils

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import androidx.core.net.toUri
import org.koin.core.context.GlobalContext

actual class LinkHandler(private val ctx: Context) {

    actual fun openLink(link: String) {
        ctx.openLink(link.toUri())
    }

}

actual val LocalLinkHandler = compositionLocalOf { LinkHandler(GlobalContext.get().get()) }