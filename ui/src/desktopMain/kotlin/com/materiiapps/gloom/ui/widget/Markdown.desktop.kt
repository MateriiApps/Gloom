package com.materiiapps.gloom.ui.widget

import com.multiplatform.webview.web.NativeWebView

actual fun <T> onWebViewCreated(view: T) {
    val nativeView = view as NativeWebView
}