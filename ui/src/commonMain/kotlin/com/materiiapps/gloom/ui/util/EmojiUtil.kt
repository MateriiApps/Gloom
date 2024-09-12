package com.materiiapps.gloom.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.materiiapps.gloom.Res
import dev.icerock.moko.resources.compose.readTextAsState
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object EmojiUtil : KoinComponent {
    private val json: Json by inject()
    private var _emojis: Map<String, String>? = null

    val emojis: Map<String, String>
        @Composable get() {
            if (_emojis != null) return _emojis!!
            val text by Res.files.emoji_json.readTextAsState()
            if (text == null) return emptyMap()
            _emojis = json.decodeFromString<Map<String, String>>(text!!)
            return _emojis!!
        }


}