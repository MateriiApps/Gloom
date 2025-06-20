package dev.materii.gloom.util

import android.content.Intent

fun Intent.isOAuthUri() = data.toString().startsWith("github://com.github.android/oauth")

fun Intent.getOAuthCode(): String? {
    if (!isOAuthUri()) return null
    val code = data?.getQueryParameter("code")
    return code?.ifBlank { null }
}