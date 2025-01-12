package dev.materii.gloom.util.deeplink

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

val LocalDeepLinkHandler: ProvidableCompositionLocal<DeepLinkHandler?> =
    staticCompositionLocalOf { null }