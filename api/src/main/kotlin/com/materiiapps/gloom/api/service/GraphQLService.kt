package com.materiiapps.gloom.api.service

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.doNotStore
import com.materiiapps.gloom.api.utils.response
import com.materiiapps.gloom.api.utils.toOptional
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.gql.AccountInfoQuery
import com.materiiapps.gloom.gql.DefaultBranchQuery
import com.materiiapps.gloom.gql.FeedQuery
import com.materiiapps.gloom.gql.FollowUserMutation
import com.materiiapps.gloom.gql.FollowersQuery
import com.materiiapps.gloom.gql.FollowingQuery
import com.materiiapps.gloom.gql.IdentifyQuery
import com.materiiapps.gloom.gql.JoinedOrgsQuery
import com.materiiapps.gloom.gql.ProfileQuery
import com.materiiapps.gloom.gql.RawMarkdownQuery
import com.materiiapps.gloom.gql.ReactMutation
import com.materiiapps.gloom.gql.ReleaseDetailsQuery
import com.materiiapps.gloom.gql.RepoDetailsQuery
import com.materiiapps.gloom.gql.RepoFileQuery
import com.materiiapps.gloom.gql.RepoFilesQuery
import com.materiiapps.gloom.gql.RepoIssuesQuery
import com.materiiapps.gloom.gql.RepoLicenseQuery
import com.materiiapps.gloom.gql.RepoListQuery
import com.materiiapps.gloom.gql.RepoNameQuery
import com.materiiapps.gloom.gql.RepoPullRequestsQuery
import com.materiiapps.gloom.gql.RepoReleasesQuery
import com.materiiapps.gloom.gql.SponsoringQuery
import com.materiiapps.gloom.gql.StarRepoMutation
import com.materiiapps.gloom.gql.StarredReposQuery
import com.materiiapps.gloom.gql.UnfollowUserMutation
import com.materiiapps.gloom.gql.UnreactMutation
import com.materiiapps.gloom.gql.UnstarRepoMutation
import com.materiiapps.gloom.gql.UserProfileQuery
import com.materiiapps.gloom.gql.type.IssueState
import com.materiiapps.gloom.gql.type.PullRequestState
import com.materiiapps.gloom.gql.type.ReactionContent
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GraphQLService(
    private val client: ApolloClient,
    private val authManager: AuthManager,
) {

    private fun <D : Operation.Data> ApolloCall<D>.addToken() =
        addHttpHeader(HttpHeaders.Authorization, "Bearer ${authManager.authToken}")

    suspend fun getAccountInfo(token: String) = withContext(Dispatchers.IO) {
        client.query(AccountInfoQuery())
            .doNotStore(true)
            .addHttpHeader(HttpHeaders.Authorization, "Bearer $token")
            .response()
    }

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

    suspend fun getRepoLicense(owner: String, name: String) = withContext(Dispatchers.IO) {
        client.query(RepoLicenseQuery(owner, name))
            .addToken()
            .response()
    }

    suspend fun getRepoFiles(owner: String, name: String, branchAndPath: String) =
        withContext(Dispatchers.IO) {
            client.query(RepoFilesQuery(owner, name, branchAndPath))
                .addToken()
                .response()
        }

    suspend fun getRepoFile(owner: String, name: String, branch: String, path: String) =
        withContext(Dispatchers.IO) {
            client.query(RepoFileQuery(owner, name, branch, path))
                .addToken()
                .response()
        }

    suspend fun getRawMarkdown(owner: String, name: String, branch: String, path: String) =
        withContext(Dispatchers.IO) {
            client.query(RawMarkdownQuery(owner, name, branch, path))
                .addToken()
                .response()
        }

    suspend fun getDefaultBranch(owner: String, name: String) = withContext(Dispatchers.IO) {
        client.query(DefaultBranchQuery(owner, name))
            .addToken()
            .response()
    }

    suspend fun getRepoIssues(
        owner: String,
        name: String,
        after: String? = null,
        states: List<IssueState> = listOf(IssueState.OPEN, IssueState.CLOSED)
    ) = withContext(Dispatchers.IO) {
        client.query(RepoIssuesQuery(owner, name, after.toOptional(), states))
            .addToken()
            .response()
    }

    suspend fun getRepoPullRequests(
        owner: String,
        name: String,
        after: String? = null,
        states: List<PullRequestState> = listOf(PullRequestState.OPEN)
    ) = withContext(Dispatchers.IO) {
        client.query(RepoPullRequestsQuery(owner, name, after.toOptional(), states))
            .addToken()
            .response()
    }

    suspend fun getRepoReleases(
        owner: String,
        name: String,
        after: String? = null
    ) = withContext(Dispatchers.IO) {
        client.query(RepoReleasesQuery(owner, name, after.toOptional()))
            .addToken()
            .response()
    }

    suspend fun getReleaseDetails(
        owner: String,
        name: String,
        tag: String,
        after: String? = null
    ) = withContext(Dispatchers.IO) {
        client.query(ReleaseDetailsQuery(owner, name, tag, after.toOptional()))
            .addToken()
            .response()
    }

    suspend fun react(id: String, reaction: ReactionContent) = withContext(Dispatchers.IO) {
        client.mutation(ReactMutation(id, reaction))
            .addToken()
            .response()
    }

    suspend fun unreact(id: String, reaction: ReactionContent) = withContext(Dispatchers.IO) {
        client.mutation(UnreactMutation(id, reaction))
            .addToken()
            .response()
    }

}