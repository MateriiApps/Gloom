package com.materiiapps.gloom.utils

import java.io.File

fun String?.ifNullOrBlank(block: () -> String) = if (isNullOrBlank()) block() else this

val IsDeveloper = isDebug || File(GloomPath, ".dev").exists()

// Gets overwritten in the platform specific entry point
var VersionName = "UNKNOWN"