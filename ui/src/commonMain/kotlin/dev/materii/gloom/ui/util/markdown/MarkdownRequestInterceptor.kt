package dev.materii.gloom.ui.util.markdown

import dev.materii.gloom.util.LinkHandler
import com.multiplatform.webview.request.RequestInterceptor
import com.multiplatform.webview.request.WebRequest
import com.multiplatform.webview.request.WebRequestInterceptResult
import com.multiplatform.webview.web.WebViewNavigator

class MarkdownRequestInterceptor(
    private val linkHandler: dev.materii.gloom.util.LinkHandler
): RequestInterceptor {

    override fun onInterceptUrlRequest(
        request: WebRequest,
        navigator: WebViewNavigator
    ): WebRequestInterceptResult {
        // This just stops markdown webviews from navigating inline
        linkHandler.openLink(request.url)
        return WebRequestInterceptResult.Reject
    }

}