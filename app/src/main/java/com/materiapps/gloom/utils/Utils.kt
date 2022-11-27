package com.materiapps.gloom.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Locale

object Utils: KoinComponent {
    private val json: Json by inject()
    private val logger: Logger by inject()

    fun @Serializable Any?.log() {
        logger.debug("Model", json.encodeToString(this))
    }
}

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

val String.parsedColor: Color
    get() {
        val langColor =
            if (length == 4) this + substring(1..3) else this

        return try {
            Color(android.graphics.Color.parseColor(langColor))
        } catch (e: Throwable) {
            Color.Black
        }
    }

fun Context.openLink(url: Uri) {
    // TODO: Setting to disable custom tabs
    openCustomTab(url.toString(), force = false)
}

fun Context.shareText(text: String) = Intent(Intent.ACTION_SEND).apply {
    putExtra(Intent.EXTRA_TEXT, text)
    type = "text/plain"
    Intent.createChooser(this, null).also {
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this@shareText.startActivity(it)
    }
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
                            themed-picture,img {
                                max-width: 100%;
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