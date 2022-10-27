package com.materiapps.gloom.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PackageInfoFlags
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.ScreenModel
import com.materiapps.gloom.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

val ScreenModel.scope: CoroutineScope
    get() = CoroutineScope(Dispatchers.IO)

fun coroutine(block: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(Dispatchers.IO).launch(block = block)
}

val Color.hexCode: String
    inline get() {
        val a: Int = (alpha * 255).toInt()
        val r: Int = (red * 255).toInt()
        val g: Int = (green * 255).toInt()
        val b: Int = (blue * 255).toInt()
        return java.lang.String.format(Locale.getDefault(), "%02X%02X%02X%02X", r, g, b, a)
    }

@Composable
fun generateMdHtml(
    source: String,
    wrap: Boolean = false,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    linkColor: Color = MaterialTheme.colorScheme.primary
): String {
        return """<html>
                    <head>
                        <meta charset="utf-8" />
                        <title>Markdown</title>
                        <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/>
                        <style>
                            body {
                                color: #${textColor.hexCode};
                            }
                            a {
                                color: #${linkColor.hexCode}!important;
                            }
                            a.anchor {
                                display: none;
                            }
                            .highlight pre, pre {
                                word-wrap: ${if(wrap) "break-word" else "normal"}; 
                                white-space: ${if(wrap) "pre-wrap" else "pre"}; 
                            } 
                        </style> 
                    </head> 
                    <body> 
                        $source 
                    </body>
                </html>"""
}