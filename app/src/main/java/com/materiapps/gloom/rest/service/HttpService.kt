package com.materiapps.gloom.rest.service

import com.materiapps.gloom.rest.utils.ApiError
import com.materiapps.gloom.rest.utils.ApiFailure
import com.materiapps.gloom.rest.utils.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class HttpService(
    val json: Json,
    val http: HttpClient,
) {

    suspend inline fun <reified T> request(builder: HttpRequestBuilder.() -> Unit = {}): ApiResponse<T> {
        var body: String? = null
        val response = try {
            val response = http.request(builder)

            if (response.status.isSuccess()) {
                body = response.bodyAsText()
                if (response.status == HttpStatusCode.NoContent)
                    ApiResponse.Empty()
                else if (T::class.java.isAssignableFrom("".javaClass))
                    ApiResponse.Success(body as T)
                else
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