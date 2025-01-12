package dev.materii.gloom.ui.widget.markdown

import com.multiplatform.webview.web.NativeWebView

actual fun <T> onWebViewCreated(view: T) {
    val nativeView = view as NativeWebView
}