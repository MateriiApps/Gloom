package com.materiapps.gloom.ui.widgets

//import android.webkit.WebView
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import com.materiapps.gloom.utils.generateMdHtml


@Composable
fun ReadMeCard(
    text: String
) {
    ElevatedCard {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Markdown(
                text
            )
        }
    }
}

@SuppressLint("ClickableViewAccessibility")
@Composable
fun Markdown(
    text: String,
    modifier: Modifier = Modifier
) {
    val state = rememberWebViewStateWithHTMLData(data = generateMdHtml(text))
    val client = object : AccompanistWebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return true
        }
    }

    WebView(
        state,
        modifier = Modifier.background(Color.Transparent),
        client = client,
        onCreated = {
            it.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            it.isVerticalScrollBarEnabled = false
            it.isHorizontalScrollBarEnabled = false
            it.setOnTouchListener { v, event -> event.action == MotionEvent.ACTION_MOVE }
            it.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    )
}