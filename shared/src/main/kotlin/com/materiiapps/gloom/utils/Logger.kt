package com.materiiapps.gloom.utils

import android.util.Log

@Suppress("unused")
class Logger {

    fun error(tag: String, message: String?, throwable: Throwable?) {
        if (message != null || throwable != null)
            Log.e(tag, message, throwable)
    }

    fun warn(tag: String, message: String, throwable: Throwable?) {
        Log.w(tag, message, throwable)
    }

    fun info(tag: String, message: String) {
        Log.i(tag, message)
    }

    fun debug(tag: String, message: String) {
        if (isDebug)
            Log.d(tag, message)
    }

}