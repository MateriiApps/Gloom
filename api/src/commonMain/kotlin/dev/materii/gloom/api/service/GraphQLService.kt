package dev.materii.gloom.api.service

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Optional
import com.apollographql.apollo.cache.normalized.doNotStore
import dev.materii.gloom.api.util.response
import dev.materii.gloom.api.util.toOptional
import dev.materii.gloom.domain.manager.AuthManager
import dev.materii.gloom.gql.*
import dev.materii.gloom.gql.type.*
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

    suspend fun getTrending(period: TrendingPeriod = TrendingPeriod.DAILY) = withContext(Dispatchers.IO) {
        client
            .query(TrendingQuery(period))
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

    suspend fun getRepoForks(
        owner: String,
        name: String,
        after: String? = null,
        count: Int? = null
    ) = withContext(Dispatchers.IO) {
        client
            .query(
                RepoForksQuery(
                    username = owner,
                    name = name,
                    cursor = after.toOptional(),
                    total = count.toOptional()
                )
            )
            .addToken()
            .response()
    }

    suspend fun getRepoContributors(
        owner: String,
        name: String,
        after: String? = null
    ) = withContext(Dispatchers.IO) {
        client.query(RepoContributorsQuery(owner, name, after.toOptional()))
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