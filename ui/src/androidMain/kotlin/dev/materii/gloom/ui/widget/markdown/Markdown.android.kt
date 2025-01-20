package dev.materii.gloom.ui.widget.markdown

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.benasher44.uuid.uuid4
import com.multiplatform.webview.web.NativeWebView

@SuppressLint("ClickableViewAccessibility")
actual fun <T> onWebViewCreated(view: T) {
    val nativeView = view as NativeWebView
    val id = uuid4().toString()

    nativeView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
    nativeView.isVerticalScrollBarEnabled = false
    nativeView.isHorizontalScrollBarEnabled = false
    nativeView.layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    nativeView.settings.javaScriptEnabled = true
}