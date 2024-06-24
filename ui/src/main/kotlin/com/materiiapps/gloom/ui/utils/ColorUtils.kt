package com.materiiapps.gloom.ui.utils

import androidx.compose.ui.graphics.Color

private val hexCodeRegex = "^#?(([0-9a-fA-F]{1,2})([0-9a-fA-F]{1,2})([0-9a-fA-F]{1,2}))$".toRegex()
private val parsedColors = mutableMapOf<String, Color>()
val String.parsedColor: Color
    get() {
        if (parsedColors.containsKey(this)) return parsedColors[this]!!
        val match = hexCodeRegex.matchEntire(this) ?: return Color.Black

        val (_, r, g, b) = match.groups.toList()
            .mapNotNull { runCatching { it?.value?.toInt(16) }.getOrNull() }

        return try {
            parsedColors[this] = Color(r, g, b)
            Color(r, g, b)
        } catch (e: Throwable) {
            Color.Black
        }
    }

val Color.hexCode: String
    inline get() {
        val a: Int = (alpha * 255).toInt()
        val r: Int = (red * 255).toInt()
        val g: Int = (green * 255).toInt()
        val b: Int = (blue * 255).toInt()
        return "%02X%02X%02X%02X".format(r, g, b, a)
    }