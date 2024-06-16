package com.materiiapps.gloom.api.repository

import com.materiiapps.gloom.api.service.GithubAuthApiService

class GithubAuthRepository(
    private val service: GithubAuthApiService
) {

    suspend fun getAccessToken(code: String) = service.getAccessToken(code)

    suspend fun deleteAccessToken(token: String) = service.deleteAccessToken(token)

}