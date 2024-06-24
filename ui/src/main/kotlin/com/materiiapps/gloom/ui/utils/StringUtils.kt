package com.materiiapps.gloom.ui.utils

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import com.materiiapps.gloom.ui.R
import com.materiiapps.gloom.utils.Constants

// Credit to rushii (github.com/DiamondMiner88)

/**
 * This formats and styles string resources into an [AnnotatedString].
 * By putting `§annotation_name§target string blah blah blah§` into the resource string it can be translated
 * (assuming `annotation_name` is not changed between translations),
 * when rendering [annotationHandler] will be called on each `annotation_name` and expected to return a target style on it.
 */
@Composable
inline fun annotatingStringResource(
    @StringRes res: Int,
    vararg args: Any,
    crossinline annotationHandler: @Composable (annotationName: String) -> SpanStyle?
): AnnotatedString {
    val string = stringResource(res, *args)

    // TODO: figure out a way to put this in remember {} while still having @Composable annotationHandler
    val markerIndexes = string
        .mapIndexedNotNull { index, elem -> index.takeIf { elem == '§' } }
        .takeIf { it.isNotEmpty() && it.size % 3 == 0 }
        ?: return AnnotatedString(string)

    return buildAnnotatedString {
        var lastIndex = 0
        var offset = 0

        for ((nameStart, nameEnd, valueEnd) in markerIndexes.chunked(3)) {
            append(string.substring(lastIndex, nameStart))
            append(string.substring(nameEnd + 1, valueEnd))

            val annotationName = string.substring(nameStart + 1, nameEnd)
            val newValueStart = nameEnd - annotationName.length - offset - 1
            val newValueEnd = valueEnd - annotationName.length - offset - 2

            addStringAnnotation(
                tag = annotationName,
                annotation = annotationName,
                start = newValueStart,
                end = newValueEnd,
            )

            annotationHandler(annotationName)?.let {
                addStyle(it, newValueStart, newValueEnd)
            }

            lastIndex = valueEnd + 1
            offset += 3 + annotationName.length
        }

        append(string.substring(lastIndex))
    }
}

@Composable
fun getFileSizeString(size: Int): String {
    return when {
        size < Constants.FILE_SIZES.KILO -> stringResource(R.string.file_size_bytes, size)
        size < Constants.FILE_SIZES.MEGA -> stringResource(
            R.string.file_size_kilobytes,
            size / Constants.FILE_SIZES.KILO
        )

        size < Constants.FILE_SIZES.GIGA -> stringResource(
            R.string.file_size_megabytes,
            size / Constants.FILE_SIZES.MEGA
        )

        else -> stringResource(R.string.file_size_gigabytes, size / Constants.FILE_SIZES.GIGA)
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
                                word-wrap: ${if (wrap) "break-word" else "normal"}; 
                                white-space: ${if (wrap) "pre-wrap" else "pre"}; 
                            } 
                        </style> 
                    </head> 
                    <body> 
                        $source 
                    </body>
                </html>"""
}