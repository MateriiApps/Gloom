package com.materiapps.gloom.rest.service

import com.materiapps.gloom.rest.utils.ApiResponse
import com.materiapps.gloom.utils.URLs
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubApiService(
    private val client: HttpService
) {

    suspend fun getRepoReadMe(owner: String, repo: String): ApiResponse<String> =
        withContext(Dispatchers.IO) {
            client.request {
                url(URLs.REPOS.README(owner, repo))
            }
        }

}