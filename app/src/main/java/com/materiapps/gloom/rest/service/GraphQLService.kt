package com.materiapps.gloom.rest.service

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.Optional
import com.materiapps.gloom.ProfileQuery
import com.materiapps.gloom.RepoListQuery
import com.materiapps.gloom.UserProfileQuery
import com.materiapps.gloom.domain.manager.AuthManager
import com.materiapps.gloom.rest.utils.response
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GraphQLService(
    private val client: ApolloClient,
    private val authManager: AuthManager,
) {

    private fun <D : Operation.Data> ApolloCall<D>.addToken() =
        addHttpHeader(HttpHeaders.Authorization, "Bearer ${authManager.authToken}")

    suspend fun getCurrentProfile() = withContext(Dispatchers.IO) {
        client.query(ProfileQuery())
            .addToken()
            .response()
    }

    suspend fun getProfile(username: String) = withContext(Dispatchers.IO) {
        client.query(UserProfileQuery(username = username))
            .addToken()
            .response()
    }

    suspend fun getRepositoriesForUser(
        username: String,
        after: String? = null,
        count: Int? = null
    ) = withContext(Dispatchers.IO) {
        client.query(
            RepoListQuery(
                username = username,
                cursor = if (after != null) Optional.present(after) else Optional.absent(),
                total = if (count != null) Optional.present(count) else Optional.absent()
            )
        )
            .addToken()
            .response()
    }

}