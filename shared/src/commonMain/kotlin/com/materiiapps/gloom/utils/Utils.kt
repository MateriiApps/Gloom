package com.materiiapps.gloom.utils

import java.io.File

fun String?.ifNullOrBlank(block: () -> String) = if (isNullOrBlank()) block() else this

val IsDeveloper = isDebug || File(GloomPath, ".dev").exists()