package com.materiapps.gloom.rest.service

import com.materiapps.gloom.BuildConfig
import com.materiapps.gloom.rest.dto.auth.AccessTokenResponse
import com.materiapps.gloom.rest.utils.ApiResponse
import com.materiapps.gloom.utils.URLs
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubAuthApiService(
    private val client: HttpService
) {

    suspend fun getAccessToken(code: String): ApiResponse<AccessTokenResponse> =
        withContext(Dispatchers.IO) {
            client.request {
                url(URLs.AUTH.ACCESS_TOKEN)
                parameter("client_id", BuildConfig.CLIENT_ID)
                parameter("client_secret", BuildConfig.CLIENT_SECRET)
                parameter("code", code)
                method = HttpMethod.Post
            }
        }

}