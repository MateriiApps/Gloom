package com.materiiapps.gloom.domain.repository

import com.materiiapps.gloom.rest.service.GithubAuthApiService

class GithubAuthRepository(
    private val service: GithubAuthApiService
) {

    suspend fun getAccessToken(code: String) = service.getAccessToken(code)

    suspend fun deleteAccessToken(token: String) = service.deleteAccessToken(token)

}