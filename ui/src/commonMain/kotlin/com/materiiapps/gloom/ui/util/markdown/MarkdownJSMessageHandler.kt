package com.materiiapps.gloom.ui.util.markdown

import com.multiplatform.webview.jsbridge.IJsMessageHandler
import com.multiplatform.webview.jsbridge.JsMessage
import com.multiplatform.webview.web.WebViewNavigator

class MarkdownJSMessageHandler: IJsMessageHandler {

    override fun handle(
        message: JsMessage,
        navigator: WebViewNavigator?,
        callback: (String) -> Unit
    ) {
        // TODO: Use for special actions such as committing suggested changes
    }

    override fun methodName(): String {
        return "MDMessage"
    }

}