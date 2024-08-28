package com.materiiapps.gloom.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.materiiapps.gloom.ui.utils.generateMdHtml
import com.materiiapps.gloom.utils.LocalLinkHandler
import com.multiplatform.webview.request.RequestInterceptor
import com.multiplatform.webview.request.WebRequest
import com.multiplatform.webview.request.WebRequestInterceptResult
import com.multiplatform.webview.web.NativeWebView
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData

@Composable
fun Markdown(
    text: String,
    modifier: Modifier
) {
    val linkHandler = LocalLinkHandler.current
    val state = rememberWebViewStateWithHTMLData(data = generateMdHtml(text))

    val interceptor = object: RequestInterceptor {
        override fun onInterceptUrlRequest(
            request: WebRequest,
            navigator: WebViewNavigator
        ): WebRequestInterceptResult {
            linkHandler.openLink(request.url)
            return WebRequestInterceptResult.Reject
        }
    }

    val navigator = rememberWebViewNavigator(requestInterceptor = interceptor)

    WebView(
        state = state,
        navigator = navigator,
        modifier = Modifier
            .background(Color.Transparent)
            .then(modifier),
        onCreated = { view: NativeWebView ->
            onWebViewCreated(view)
        }
    )
}

/**
 * [T] should correspond to [NativeWebView] but I can't use that here directly
 */
expect fun <T> onWebViewCreated(view: T)