package com.materiapps.gloom.rest.service

import com.materiapps.gloom.rest.utils.ApiError
import com.materiapps.gloom.rest.utils.ApiFailure
import com.materiapps.gloom.rest.utils.ApiResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class HttpService(
    val json: Json,
    val http: HttpClient
) {

    suspend inline fun <reified T> request(builder: HttpRequestBuilder.() -> Unit = {}): ApiResponse<T> {
        var body: String? = null

        val response = try {
            val response = http.request(builder)

            if (response.status.isSuccess()) {
                body = response.bodyAsText()

                ApiResponse.Success(json.decodeFromString<T>(body))
            } else {
                body = try {
                    response.bodyAsText()
                } catch (t: Throwable) {
                    null
                }

                ApiResponse.Error(ApiError(response.status, body))
            }
        } catch (e: Throwable) {
            ApiResponse.Failure(ApiFailure(e, body))
        }
        return response
    }

}