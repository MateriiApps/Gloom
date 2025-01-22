package dev.materii.gloom.ui.widget.markdown

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.apollographql.apollo.api.http.internal.urlEncode
import com.benasher44.uuid.uuid4
import dev.materii.gloom.Res
import dev.materii.gloom.ui.util.markdown.MarkdownJSMessageHandler
import dev.materii.gloom.ui.util.markdown.MarkdownRequestInterceptor
import com.multiplatform.webview.jsbridge.rememberWebViewJsBridge
import com.multiplatform.webview.web.NativeWebView
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import dev.icerock.moko.resources.compose.readTextAsState
import dev.materii.gloom.ui.theme.CodeTheme
import dev.materii.gloom.ui.theme.gloomColorScheme
import dev.materii.gloom.ui.util.markdown.MarkdownUtil
import dev.materii.gloom.util.LocalLinkHandler

private var MarkdownTemplate = "" // Cache the loaded markdown html template
private val PrefersLightRx   = "prefers-color-scheme:\\s*light".toRegex(RegexOption.IGNORE_CASE)
private val PrefersDarkRx    = "prefers-color-scheme:\\s*dark".toRegex(RegexOption.IGNORE_CASE)

/**
 * Wrapper around a WebView to automatically add styling
 * and fix a couple issues.
 *
 * @see `shared/src/commonMain/moko-resources/assets/markdown`
 *
 * @param html The rendered HTML, returned from the GitHub API
 * @param modifier The [Modifier] used to style this [Markdown] component
 */
@Composable
fun Markdown(
    html: String,
    modifier: Modifier
) {
    val linkHandler = LocalLinkHandler.current

    val messageHandler = MarkdownJSMessageHandler()
    val interceptor = MarkdownRequestInterceptor(linkHandler)
    val navigator = rememberWebViewNavigator(requestInterceptor = interceptor)
    val bridge = rememberWebViewJsBridge(navigator)

    val id = remember { uuid4().toString() }
    val isLight = MaterialTheme.colorScheme.background.luminance() > 0.5f
    val markdown = remember(html) {
        html
            // TODO: Allow for jumping to headings, this will require special handling
//            .replace("<a href=\"#", "<a href=\"gloom://github.com/?anchor=")
            .replace("\\", "\\\\")
            .replace("\n", "\\n")
            .replace("\"", "\\\"")

            // This is a weird hack to get around how Android determines 'prefers-color-scheme'
            // media queries. Since we theme independently of Android, we have to calculate
            // this ourselves. To do this, we use 'min-width: 0' as a stand in for 'true' and
            // 'min-width: -1' as a stand-in for false.
            .replace(PrefersLightRx, "min-width: ${if (isLight) 0 else -1}")
            .replace(PrefersDarkRx, "min-width: ${if (!isLight) 0 else -1}")

            .urlEncode() // So that we can safely pass it to the JavaScript side
    }

    val template = MarkdownTemplate.ifBlank {
        val file by Res.assets.markdown.template_html.readTextAsState()
        MarkdownTemplate = file ?: ""; MarkdownTemplate
    }

    val colorScheme = MaterialTheme.colorScheme
    val gloomColorScheme = MaterialTheme.gloomColorScheme
    val state = rememberWebViewStateWithHTMLData(data = MarkdownUtil.injectAppTheme(template, isLight, colorScheme, gloomColorScheme, CodeTheme.getDefault()))

    LaunchedEffect(state.isLoading) {
        // See shared/src/commonMain/moko-resources/assets/markdown/markdown.js
        if (!state.isLoading) navigator.loadUrl("javascript:gloom.load(\"$id\", \"$markdown\")")
    }

    LaunchedEffect(markdown) {
        navigator.loadUrl("javascript:gloom.load(\"$id\", \"$markdown\")")
    }

    WebView(
        state = state,
        navigator = navigator,
        webViewJsBridge = bridge,
        onCreated = { view: NativeWebView ->
            onWebViewCreated(view)
            bridge.register(messageHandler)
        },
        onDispose = { bridge.unregister(messageHandler) },
        modifier = Modifier
            .background(Color.Transparent)
            .then(modifier)
    )
}

/**
 * [T] should correspond to [NativeWebView] but I can't use that here directly
 */
expect fun <T> onWebViewCreated(view: T)