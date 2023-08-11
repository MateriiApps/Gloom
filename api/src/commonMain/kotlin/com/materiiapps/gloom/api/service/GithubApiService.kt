package com.materiiapps.gloom.api.service

import com.materiiapps.gloom.domain.manager.AuthManager

class GithubApiService(
    private val client: HttpService,
    private val authManager: AuthManager
) {
    // In case something is needed that's not in the GraphQL API
}