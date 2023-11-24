package com.materiiapps.gloom.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.snipme.highlights.model.SyntaxLanguage

fun SyntaxLanguage.Companion.fromExtension(ext: String): SyntaxLanguage =
    when (ext.replaceFirst(".", "").lowercase()) {
        "c" -> SyntaxLanguage.C
        "coffee", "litcoffee" -> SyntaxLanguage.COFFEESCRIPT
        "h", "o", "cc", "cpp" -> SyntaxLanguage.CPP
        "cs" -> SyntaxLanguage.CSHARP
        "java" -> SyntaxLanguage.JAVA
        "js", "mjs", "ts", "jsx", "tsx" -> SyntaxLanguage.JAVASCRIPT
        "kt", "kts" -> SyntaxLanguage.KOTLIN
        "pl" -> SyntaxLanguage.PERL
        "py" -> SyntaxLanguage.PYTHON
        "rs" -> SyntaxLanguage.RUST
        "rb" -> SyntaxLanguage.RUBY
        "sh", "zsh", "fsh" -> SyntaxLanguage.SHELL
        "swift" -> SyntaxLanguage.SWIFT
        else -> SyntaxLanguage.DEFAULT
    }

val Int.digits: Int
    get() = toString().length

@Composable
fun Int.padLineNumber(maxDigits: Int): String {
    val intStr = toString()
    return remember(intStr, maxDigits) { intStr.padStart(maxDigits) }
}