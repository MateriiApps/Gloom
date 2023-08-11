package com.materiiapps.gloom.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log

@Suppress("unused")
actual class Logger(private val context: Context) {

    actual fun error(tag: String, message: String?, throwable: Throwable?) {
        if (message != null || throwable != null)
            Log.e(tag, message, throwable)
    }

    actual fun warn(tag: String, message: String, throwable: Throwable?) {
        Log.w(tag, message, throwable)
    }

    actual fun info(tag: String, message: String) {
        Log.i(tag, message)
    }

    actual fun debug(tag: String, message: String) {
        val isDebug =
            (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0 /* TODO: Move this into some util function */
        if (isDebug)
            Log.d(tag, message)
    }
}