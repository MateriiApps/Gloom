package com.materiiapps.gloom.util

import android.util.Log

@Suppress("unused")
actual class Logger {

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
        if (isDebug)
            Log.d(tag, message)
    }
}