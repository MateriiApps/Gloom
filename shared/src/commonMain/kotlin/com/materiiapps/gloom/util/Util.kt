package com.materiiapps.gloom.util

import com.materiiapps.gloom.Res
import com.mikepenz.aboutlibraries.entity.Library
import java.io.File

inline fun String?.ifNullOrBlank(block: () -> String) = if (isNullOrBlank()) block() else this

val Library.author: String
    get() = developers.takeIf { it.isNotEmpty() }?.map { it.name }?.joinToString(", ") ?: organization?.name ?: ""

val IsDeveloper = isDebug || File(GloomPath, ".dev").exists()

// Gets overwritten in the platform specific entry point
var VersionName = "UNKNOWN"
