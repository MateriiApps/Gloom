package com.materiiapps.gloom.utils

fun String?.ifNullOrBlank(block: () -> String) = if (isNullOrBlank()) block() else this