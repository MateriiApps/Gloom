package com.materiiapps.gloom.domain.manager

import android.content.Context
import android.content.Intent

actual class ShareManager(
    private val context: Context
) {

    actual fun shareText(text: String) {
        Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
            Intent.createChooser(this, null).also {
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(it)
            }
        }
    }

}