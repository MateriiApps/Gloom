package com.materiiapps.gloom.utils

import android.util.Log
import com.materiiapps.gloom.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.logging.*

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
        if (BuildConfig.DEBUG)
            Log.d(tag, message)
    }
}

fun <T : HttpClientEngineConfig> HttpClientConfig<T>.installLogging(loggerDI: Logger) {
    install(Logging) {
        level = LogLevel.BODY
        logger = object : io.ktor.client.plugins.logging.Logger {
            override fun log(message: String) {
                loggerDI.debug("HTTP", message)
            }
        }
    }
}