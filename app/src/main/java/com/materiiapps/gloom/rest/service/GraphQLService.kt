package com.materiiapps.gloom.rest.service

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.Optional
import com.materiiapps.gloom.gql.FeedQuery
import com.materiiapps.gloom.gql.FollowUserMutation
import com.materiiapps.gloom.gql.FollowersQuery
import com.materiiapps.gloom.gql.FollowingQuery
import com.materiiapps.gloom.gql.IdentifyQuery
import com.materiiapps.gloom.gql.JoinedOrgsQuery
import com.materiiapps.gloom.gql.ProfileQuery
import com.materiiapps.gloom.gql.RepoListQuery
import com.materiiapps.gloom.gql.SponsoringQuery
import com.materiiapps.gloom.gql.StarRepoMutation
import com.materiiapps.gloom.gql.StarredReposQuery
import com.materiiapps.gloom.gql.UnfollowUserMutation
import com.materiiapps.gloom.gql.UnstarRepoMutation
import com.materiiapps.gloom.gql.UserProfileQuery
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.rest.utils.response
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

    suspend fun getStarredRepositories(
        username: String,
        after: String? = null,
        count: Int? = null
    ) = withContext(Dispatchers.IO) {
        client.query(
            StarredReposQuery(
                username = username,
                cursor = if (after != null) Optional.present(after) else Optional.absent(),
                total = if (count != null) Optional.present(count) else Optional.absent()
            )
        )
            .addToken()
            .response()
    }

    suspend fun getJoinedOrgs(
        username: String,
        after: String? = null,
        count: Int? = null
    ) = withContext(Dispatchers.IO) {
        client.query(
            JoinedOrgsQuery(
                username = username,
                cursor = if (after != null) Optional.present(after) else Optional.absent(),
                total = if (count != null) Optional.present(count) else Optional.absent()
            )
        )
            .addToken()
            .response()
    }

    suspend fun getFollowers(
        username: String,
        after: String? = null,
        count: Int? = null
    ) = withContext(Dispatchers.IO) {
        client.query(
            FollowersQuery(
                username = username,
                cursor = if (after != null) Optional.present(after) else Optional.absent(),
                total = if (count != null) Optional.present(count) else Optional.absent()
            )
        )
            .addToken()
            .response()
    }

    suspend fun getFollowing(
        username: String,
        after: String? = null,
        count: Int? = null
    ) = withContext(Dispatchers.IO) {
        client.query(
            FollowingQuery(
                username = username,
                cursor = if (after != null) Optional.present(after) else Optional.absent(),
                total = if (count != null) Optional.present(count) else Optional.absent()
            )
        )
            .addToken()
            .response()
    }

    suspend fun getSponsoring(
        username: String,
        after: String? = null,
        count: Int? = null
    ) = withContext(Dispatchers.IO) {
        client.query(
            SponsoringQuery(
                username = username,
                cursor = if (after != null) Optional.present(after) else Optional.absent(),
                total = if (count != null) Optional.present(count) else Optional.absent()
            )
        )
            .addToken()
            .response()
    }

    suspend fun followUser(id: String) = withContext(Dispatchers.IO) {
        client.mutation(FollowUserMutation(id))
            .addToken()
            .response()
    }

    suspend fun unfollowUser(id: String) = withContext(Dispatchers.IO) {
        client.mutation(UnfollowUserMutation(id))
            .addToken()
            .response()
    }

    suspend fun getFeed(cursor: String? = null) = withContext(Dispatchers.IO) {
        client.query(
            FeedQuery(
                after = if (cursor != null) Optional.present(cursor) else Optional.absent()
            )
        )
            .addToken()
            .response()
    }

    suspend fun starRepo(id: String) = withContext(Dispatchers.IO) {
        client.mutation(StarRepoMutation(id))
            .addToken()
            .response()
    }

    suspend fun unstarRepo(id: String) = withContext(Dispatchers.IO) {
        client.mutation(UnstarRepoMutation(id))
            .addToken()
            .response()
    }

    suspend fun identify() = withContext(Dispatchers.IO) {
        client.query(IdentifyQuery())
            .addToken()
            .response()
    }

}