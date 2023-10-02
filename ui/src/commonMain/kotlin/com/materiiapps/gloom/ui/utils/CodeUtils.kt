package com.materiiapps.gloom.ui.utils

import dev.snipme.highlights.model.SyntaxLanguage

fun SyntaxLanguage.Companion.fromExtension(ext: String): SyntaxLanguage = when(ext.replaceFirst(".", "").lowercase()) {
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