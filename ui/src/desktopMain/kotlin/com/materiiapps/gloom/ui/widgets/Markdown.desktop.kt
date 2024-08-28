package com.materiiapps.gloom.ui.widgets

import com.multiplatform.webview.web.NativeWebView

actual fun <T> onWebViewCreated(view: T) {
    val nativeView = view as NativeWebView
}