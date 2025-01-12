package dev.materii.gloom.api.repository

import dev.materii.gloom.api.service.GithubAuthApiService

class GithubAuthRepository(
    private val service: GithubAuthApiService
) {

    suspend fun getAccessToken(code: String) = service.getAccessToken(code)

    suspend fun deleteAccessToken(token: String) = service.deleteAccessToken(token)

}