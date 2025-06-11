package dev.materii.gloom.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.util.getString

// Credit to rushii (github.com/rushiiMachine)

/**
 * This formats and styles string resources into an [AnnotatedString].
 * By putting `§annotation_name§target string blah blah blah§` into the resource string it can be translated
 * (assuming `annotation_name` is not changed between translations),
 * when rendering [annotationHandler] will be called on each `annotation_name` and expected to return a target style on it.
 */
@Composable
inline fun annotatingStringResource(
    res: StringResource,
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
        size < dev.materii.gloom.util.Constants.FILE_SIZES.KILO -> getString(Res.strings.file_size_bytes, size)
        size < dev.materii.gloom.util.Constants.FILE_SIZES.MEGA -> getString(
            Res.strings.file_size_kilobytes,
            size / dev.materii.gloom.util.Constants.FILE_SIZES.KILO
        )

        size < dev.materii.gloom.util.Constants.FILE_SIZES.GIGA -> getString(
            Res.strings.file_size_megabytes,
            size / dev.materii.gloom.util.Constants.FILE_SIZES.MEGA
        )

        else                                                    -> getString(
            Res.strings.file_size_gigabytes,
            size / dev.materii.gloom.util.Constants.FILE_SIZES.GIGA
        )
    }
}