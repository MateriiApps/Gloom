package com.materiiapps.gloom.ui.widgets

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.ViewGroup
import com.multiplatform.webview.web.NativeWebView

@SuppressLint("ClickableViewAccessibility")
actual fun <T> onWebViewCreated(view: T) {
    val nativeView = view as NativeWebView

    nativeView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
    nativeView.isVerticalScrollBarEnabled = false
    nativeView.isHorizontalScrollBarEnabled = false
    nativeView.setOnTouchListener { _, event -> event.action == MotionEvent.ACTION_MOVE }
    nativeView.layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
}