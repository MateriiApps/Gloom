package com.materiiapps.gloom.utils

import androidx.compose.runtime.ProvidableCompositionLocal

expect class LinkHandler {
    fun openLink(link: String)
}

expect val LocalLinkHandler: ProvidableCompositionLocal<LinkHandler>