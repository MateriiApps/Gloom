package com.materiiapps.gloom.ui.widgets

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import com.materiiapps.gloom.ui.utils.generateMdHtml
import com.materiiapps.gloom.utils.LocalLinkHandler

@Composable
@SuppressLint("ClickableViewAccessibility")
fun Markdown(
    text: String,
    modifier: Modifier
) {
    val linkHandler = LocalLinkHandler.current
    val state = rememberWebViewStateWithHTMLData(data = generateMdHtml(text))
    val client = object : AccompanistWebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (request != null) linkHandler.openLink(request.url.toString())
            return true
        }
    }

    WebView(
        state,
        modifier = Modifier
            .background(Color.Transparent)
            .then(modifier),
        client = client,
        onCreated = {
            it.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            it.isVerticalScrollBarEnabled = false
            it.isHorizontalScrollBarEnabled = false
            it.setOnTouchListener { _, event -> event.action == MotionEvent.ACTION_MOVE }
            it.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    )
}