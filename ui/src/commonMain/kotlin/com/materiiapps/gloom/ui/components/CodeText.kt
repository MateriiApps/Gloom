package com.materiiapps.gloom.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.materiiapps.gloom.ui.utils.fromExtension
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.BoldHighlight
import dev.snipme.highlights.model.ColorHighlight
import dev.snipme.highlights.model.SyntaxLanguage
import dev.snipme.highlights.model.SyntaxTheme

@Composable
fun CodeText(
    text: String,
    extension: String,
    theme: SyntaxTheme,
    softWrap: Boolean = false,
    fontSize: TextUnit = 13.sp,
    modifier: Modifier = Modifier
) {
    val hl = remember(text, theme) {
        Highlights
            .default()
            .getBuilder()
            .code(text)
            .language(SyntaxLanguage.fromExtension(extension))
            .theme(theme)
            .build()
    }
    val highlights = remember(hl) { hl.getHighlights() }

    Text(
        text = buildAnnotatedString {
            append(hl.getCode())

            if(!(extension.isBlank() || extension == ".txt")) {
                highlights
                    .filterIsInstance<ColorHighlight>()
                    .forEach {
                        addStyle(
                            SpanStyle(color = Color(it.rgb).copy(alpha = 1f)),
                            start = it.location.start,
                            end = it.location.end
                        )
                    }

                highlights
                    .filterIsInstance<BoldHighlight>()
                    .forEach {
                        addStyle(
                            SpanStyle(fontWeight = FontWeight.Bold),
                            start = it.location.start,
                            end = it.location.end
                        )
                    }
            }
        },
        fontFamily = FontFamily.Monospace,
        softWrap = softWrap,
        fontSize = fontSize,
        modifier = modifier
    )
}