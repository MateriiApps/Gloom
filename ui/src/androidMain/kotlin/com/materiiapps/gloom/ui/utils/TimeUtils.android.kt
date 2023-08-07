package com.materiiapps.gloom.ui.utils

import kotlinx.datetime.Instant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun Instant.format(format: String): String = SimpleDateFormat(
    format,
    Locale.getDefault()
).format(Date(toEpochMilliseconds()))