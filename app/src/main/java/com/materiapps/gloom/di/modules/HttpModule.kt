package com.materiapps.gloom.di.modules

import com.materiapps.gloom.BuildConfig
import com.materiapps.gloom.utils.Logger
import com.materiapps.gloom.utils.installLogging
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun httpModule() = module {

    fun provideAuthClient(
        json: Json,
        logger: Logger
    ): HttpClient {
        return HttpClient(CIO) {
            defaultRequest {
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                header(HttpHeaders.UserAgent, UserAgent)
                header(HttpHeaders.AcceptLanguage, "en-US")
            }
            install(HttpRequestRetry) {
                maxRetries = 5
                retryIf { _, httpResponse ->
                    !httpResponse.status.isSuccess()
                }
                retryOnExceptionIf { _, error ->
                    error is HttpRequestTimeoutException
                }
                delayMillis { retry ->
                    retry * 1000L
                }
            }
            install(ContentNegotiation) {
                json(json)
            }
            if (BuildConfig.DEBUG)
                installLogging(logger)
        }
    }

    single {
        Json {
            ignoreUnknownKeys = true
        }
    }

    single(named("Auth")) {
        provideAuthClient(get(), get())
    }

}