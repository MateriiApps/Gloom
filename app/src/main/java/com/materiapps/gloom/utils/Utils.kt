package com.materiapps.gloom.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Context.openUrl(url: String) {
    val intent = CustomTabsIntent.Builder().build()
    intent.intent.addFlags(
        Intent.FLAG_ACTIVITY_NEW_TASK
    )

    intent.launchUrl(this, Uri.parse(url))
}

val ScreenModel.scope: CoroutineScope
    get() = CoroutineScope(Dispatchers.IO)

fun coroutine(block: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(Dispatchers.IO).launch(block = block)
}