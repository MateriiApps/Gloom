package com.materiiapps.gloom.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.domain.manager.PreferenceManager
import com.materiiapps.gloom.domain.manager.Theme
import dev.snipme.highlights.model.SyntaxTheme
import dev.snipme.highlights.model.SyntaxThemes
import org.koin.androidx.compose.get

data class CodeTheme(
    val background: Color,
    val linesBackground: Color,
    val linesContent: Color,
    val selectedHighlight: Color,
    val code: Color,
    val keyword: Color,
    val string: Color,
    val literal: Color,
    val comment: Color,
    val metadata: Color,
    val multilineComment: Color,
    val punctuation: Color,
    val mark: Color
) {

    constructor(
        background: Color,
        linesBackground: Color,
        linesContent: Color,
        selectedHighlight: Color,
        syntaxTheme: SyntaxTheme
    ): this(
        background = background,
        linesBackground = linesBackground,
        linesContent = linesContent,
        selectedHighlight = selectedHighlight,
        code = Color(syntaxTheme.code),
        keyword = Color(syntaxTheme.keyword),
        string = Color(syntaxTheme.string),
        literal = Color(syntaxTheme.literal),
        comment = Color(syntaxTheme.comment),
        metadata = Color(syntaxTheme.metadata),
        multilineComment = Color(syntaxTheme.multilineComment),
        punctuation = Color(syntaxTheme.punctuation),
        mark = Color(syntaxTheme.mark)
    )

    val syntaxTheme = SyntaxTheme(
        key = hashCode().toString(),
        code = code.toArgb(),
        keyword = keyword.toArgb(),
        string = string.toArgb(),
        literal = literal.toArgb(),
        comment = comment.toArgb(),
        metadata = metadata.toArgb(),
        multilineComment = multilineComment.toArgb(),
        punctuation = punctuation.toArgb(),
        mark = mark.toArgb()
    )

    companion object {

        @Composable
        fun getDefault(): CodeTheme {
            val prefs: PreferenceManager = get()
            val isSystemInDarkTheme = isSystemInDarkTheme()
            val darkMode = remember(prefs.theme, isSystemInDarkTheme) {
                (prefs.theme == Theme.DARK && prefs.theme != Theme.LIGHT) || (prefs.theme == Theme.SYSTEM  && isSystemInDarkTheme)
            }
            val syntaxTheme = remember(darkMode) { SyntaxThemes.pastel(darkMode = darkMode) }

            return CodeTheme(
                background = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp).copy(alpha = 0.2f),
                linesBackground = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                linesContent = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                selectedHighlight = Color(0xFFFF9800), // Orange
                syntaxTheme = syntaxTheme
            )
        }

    }

}