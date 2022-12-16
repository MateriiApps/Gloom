package com.materiapps.gloom.rest.service

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.Optional
import com.materiapps.gloom.FeedQuery
import com.materiapps.gloom.FollowUserMutation
import com.materiapps.gloom.FollowersQuery
import com.materiapps.gloom.FollowingQuery
import com.materiapps.gloom.IdentifyQuery
import com.materiapps.gloom.JoinedOrgsQuery
import com.materiapps.gloom.ProfileQuery
import com.materiapps.gloom.RepoDetailsQuery
import com.materiapps.gloom.RepoListQuery
import com.materiapps.gloom.RepoNameQuery
import com.materiapps.gloom.SponsoringQuery
import com.materiapps.gloom.StarRepoMutation
import com.materiapps.gloom.StarredReposQuery
import com.materiapps.gloom.UnfollowUserMutation
import com.materiapps.gloom.UnstarRepoMutation
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

    suspend fun getRepoName(owner: String, name: String) = withContext(Dispatchers.IO) {
        client.query(RepoNameQuery(owner, name))
            .addToken()
            .response()
    }

    suspend fun getRepoDetails(owner: String, name: String) = withContext(Dispatchers.IO) {
        client.query(RepoDetailsQuery(owner, name))
            .addToken()
            .response()
    }

}