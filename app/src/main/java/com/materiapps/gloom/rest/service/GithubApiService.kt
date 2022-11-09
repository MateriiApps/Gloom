package com.materiapps.gloom.rest.service

import com.materiapps.gloom.domain.manager.AuthManager
import com.materiapps.gloom.rest.utils.ApiResponse
import com.materiapps.gloom.utils.URLs
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubApiService(
    private val client: HttpService,
    private val authManager: AuthManager
) {

}