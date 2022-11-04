package com.materiapps.gloom.ui.widgets

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import com.materiapps.gloom.R
import com.materiapps.gloom.utils.generateMdHtml
import com.materiapps.gloom.utils.openLink


@Composable
fun ReadMeCard(
    text: String,
    user: String
) {
    var isCollapsed by remember {
        mutableStateOf(true)
    }

    ElevatedCard {
        Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                    )) {
                        append(user)

                        withStyle(SpanStyle(
                            fontFamily = FontFamily.Default
                        )) {
                            append(" / ")

                            withStyle(SpanStyle(
                                color = MaterialTheme.colorScheme.onSurface
                            )) {
                                append("README")
                            }
                        }

                        append(".md")
                    }
                },
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp))
                    .padding(5.dp)
            )
        }
        Box(
            modifier = if (isCollapsed) Modifier.height(160.dp) else Modifier
        ) {
            Markdown(
                text,
                modifier = if (isCollapsed)
                    Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                else
                    Modifier.padding(16.dp)
            )
            if (isCollapsed) Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                            )
                        )
                    )
                    .padding(10.dp)
            ) {
                FilledTonalButton(
                    onClick = { isCollapsed = false },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(ButtonDefaults.IconSpacing),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDownward,
                            null,
                            modifier = Modifier.size(17.dp)
                        )
                        Text(stringResource(R.string.read_more))
                    }
                }
            }
        }
    }
}

@Composable
@SuppressLint("ClickableViewAccessibility")
fun Markdown(
    text: String,
    modifier: Modifier = Modifier
) {
    val ctx = LocalContext.current
    val state = rememberWebViewStateWithHTMLData(data = generateMdHtml(text))
    val client = object : AccompanistWebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if(request != null) ctx.openLink(request.url)
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