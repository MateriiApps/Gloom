package com.materiiapps.gloom.utils.deeplinks

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

val LocalDeepLinkHandler: ProvidableCompositionLocal<DeepLinkHandler?> =
    staticCompositionLocalOf { null }