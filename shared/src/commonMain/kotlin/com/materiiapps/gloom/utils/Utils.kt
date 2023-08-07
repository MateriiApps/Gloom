package com.materiiapps.gloom.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Suppress("unused")
object Utils : KoinComponent {
    private val json: Json by inject()
    private val logger: Logger by inject()

    fun @Serializable Any?.log() {
        logger.debug("Model", json.encodeToString(this))
    }
}

fun coroutine(block: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(Dispatchers.IO).launch(block = block)
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.shareText(text: String) = Intent(Intent.ACTION_SEND).apply {
    putExtra(Intent.EXTRA_TEXT, text)
    type = "text/plain"
    Intent.createChooser(this, null).also {
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this@shareText.startActivity(it)
    }
}

fun String?.ifNullOrBlank(block: () -> String) = if (isNullOrBlank()) block() else this