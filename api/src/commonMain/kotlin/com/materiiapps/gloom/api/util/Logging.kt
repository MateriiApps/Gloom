package com.materiiapps.gloom.api.util

import com.materiiapps.gloom.util.Logger
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging

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