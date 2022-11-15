package com.materiapps.gloom.utils

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object EmojiUtils: KoinComponent {
    private val context: Context by inject()
    private val json: Json by inject()

    val emojis by lazy {
        json.decodeFromString<Map<String, String>>(String(context.assets.open("data/emoji.json").readBytes()))
    }

}