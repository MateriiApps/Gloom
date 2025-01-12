package dev.materii.gloom.util.deeplink

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

typealias DeepLinkContent = @Composable (DeepLinkHandler) -> Unit

private var isMounted = false

@Composable
fun ComponentActivity.DeepLinkWrapper(
    content: DeepLinkContent
) {
    val handler = remember {
        DeepLinkHandler()
    }

    LaunchedEffect(Unit) {
        handler.handle(intent)
        addOnNewIntentListener {
            handler.handle(it)
        }
    }

    CompositionLocalProvider(LocalDeepLinkHandler provides handler) {
        content(handler).also {
            isMounted = true
        }
    }
}