package com.materiapps.gloom.rest.service

import com.materiapps.gloom.BuildConfig
import com.materiapps.gloom.rest.dto.auth.AccessTokenResponse
import com.materiapps.gloom.rest.utils.ApiResponse
import com.materiapps.gloom.utils.Credentials
import com.materiapps.gloom.utils.URLs
import io.ktor.client.request.*
import io.ktor.client.request.forms.FormDataContent
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
                setBody(
                    FormDataContent(Parameters.build {
                        append("client_id", Credentials.CLIENT_ID)
                        append("client_secret", Credentials.CLIENT_SECRET)
                        append("code", code)
                    })
                )
                method = HttpMethod.Post
            }
        }

}