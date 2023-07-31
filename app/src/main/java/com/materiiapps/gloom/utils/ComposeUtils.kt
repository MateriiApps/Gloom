package com.materiiapps.gloom.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materiiapps.gloom.gql.type.ReactionContent
import java.util.Locale
import android.graphics.Color as AndroidColor

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
        val colorStr = if (!langColor.startsWith("#")) "#$langColor" else langColor

        return try {
            Color(AndroidColor.parseColor(colorStr))
        } catch (e: Throwable) {
            Color.Black
        }
    }

val REACTION_EMOJIS = mapOf(
    ReactionContent.HEART to "♥",
    ReactionContent.CONFUSED to "😕",
    ReactionContent.EYES to "👀",
    ReactionContent.HOORAY to "🎉",
    ReactionContent.LAUGH to "😄",
    ReactionContent.ROCKET to "🚀",
    ReactionContent.THUMBS_UP to "👍",
    ReactionContent.THUMBS_DOWN to "👎",
    ReactionContent.UNKNOWN__ to "❓"
)

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
