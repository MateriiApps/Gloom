package com.materiiapps.gloom.utils

import android.content.Intent

fun Intent.isOAuthUri() = data.toString().startsWith("github://com.github.android/oauth")

fun Intent.getOAuthCode(): String? {
    if(!isOAuthUri()) return null
    return data?.getQueryParameter("code")
}