package com.materiapps.gloom.domain.repository

import com.materiapps.gloom.rest.service.GithubApiService

class GithubRepository(
    private val service: GithubApiService
) {

    suspend fun getRepoReadMe(owner: String, repo: String) = service.getRepoReadMe(owner, repo)

}