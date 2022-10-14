package com.materiapps.gloom.rest.service

import com.materiapps.gloom.BuildConfig
import com.materiapps.gloom.rest.dto.auth.AccessTokenResponse
import com.materiapps.gloom.utils.URLs
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubAuthApiService(
    val client: HttpClient
) {

    suspend fun getAccessToken(code: String): AccessTokenResponse = withContext(Dispatchers.IO) {
        client.post(URLs.AUTH.ACCESS_TOKEN) {
            parameter("client_id", BuildConfig.CLIENT_ID)
            parameter("client_secret", BuildConfig.CLIENT_SECRET)
            parameter("code", code)
        }.body()
    }

    suspend fun refreshAccessToken(refreshToken: String): AccessTokenResponse =
        withContext(Dispatchers.IO) {
            client.post(URLs.AUTH.ACCESS_TOKEN) {
                parameter("client_id", BuildConfig.CLIENT_ID)
                parameter("client_secret", BuildConfig.CLIENT_SECRET)
                parameter("refresh_token", refreshToken)
                parameter("grant_type", "refresh_token")
            }.body()
        }

}