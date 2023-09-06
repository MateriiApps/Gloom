package com.materiiapps.gloom.utils

import androidx.compose.runtime.ProvidableCompositionLocal

expect class LinkHandler {
    // Custom tabs only supported on Android
    fun openLink(link: String, forceCustomTab: Boolean = false)
}

expect val LocalLinkHandler: ProvidableCompositionLocal<LinkHandler>