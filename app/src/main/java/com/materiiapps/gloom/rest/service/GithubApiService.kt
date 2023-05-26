package com.materiiapps.gloom.rest.service

import com.materiiapps.gloom.domain.manager.AuthManager

class GithubApiService(
    private val client: HttpService,
    private val authManager: AuthManager
) {

}