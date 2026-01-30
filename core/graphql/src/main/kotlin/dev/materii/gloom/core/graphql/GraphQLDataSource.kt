package dev.materii.gloom.core.graphql

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.cache.normalized.doNotStore
import dev.materii.gloom.core.common.api.graphql.GraphQLResponseFlow
import dev.materii.gloom.core.common.api.graphql.response
import dev.materii.gloom.core.graphql.type.IssueState
import dev.materii.gloom.core.graphql.type.PullRequestState
import dev.materii.gloom.core.graphql.type.ReactionContent
import dev.materii.gloom.core.graphql.type.TrendingPeriod
import dev.materii.gloom.core.graphql.util.toOptional

interface GraphQLDataSource {

    // Account

    fun getAccountInfo(token: String): GraphQLResponseFlow<AccountInfoQuery.Data>

    fun identify(): GraphQLResponseFlow<IdentifyQuery.Data>

    // Starrable

    fun star(id: String): GraphQLResponseFlow<StarMutation.Data>

    fun unstar(id: String): GraphQLResponseFlow<UnstarMutation.Data>

    // Reactable

    fun react(id: String, reaction: ReactionContent): GraphQLResponseFlow<ReactMutation.Data>

    fun unreact(id: String, reaction: ReactionContent): GraphQLResponseFlow<UnreactMutation.Data>

    // Profile

    fun getCurrentProfile(): GraphQLResponseFlow<ProfileQuery.Data>

    fun getProfile(login: String): GraphQLResponseFlow<UserProfileQuery.Data>

    fun getUserRepositories(login: String, after: String? = null, count: Int = 30): GraphQLResponseFlow<RepoListQuery.Data>

    fun getStarredRepositories(login: String, after: String? = null, count: Int = 30): GraphQLResponseFlow<StarredReposQuery.Data>

    fun getJoinedOrgs(login: String, after: String? = null, count: Int = 30): GraphQLResponseFlow<JoinedOrgsQuery.Data>

    fun getFollowers(login: String, after: String? = null, count: Int = 30): GraphQLResponseFlow<FollowersQuery.Data>

    fun getFollowing(login: String, after: String? = null, count: Int = 30): GraphQLResponseFlow<FollowingQuery.Data>

    fun getSponsoring(login: String, after: String? = null, count: Int = 30): GraphQLResponseFlow<SponsoringQuery.Data>

    fun followUser(id: String): GraphQLResponseFlow<FollowUserMutation.Data>

    fun unfollowUser(id: String): GraphQLResponseFlow<UnfollowUserMutation.Data>

    // Feed

    fun getFeed(after: String? = null): GraphQLResponseFlow<FeedQuery.Data>

    fun getTrending(period: TrendingPeriod = TrendingPeriod.DAILY): GraphQLResponseFlow<TrendingQuery.Data>

    // Repositories

    fun getRepoName(owner: String, name: String): GraphQLResponseFlow<RepoNameQuery.Data>

    fun getRepoDetails(owner: String, name: String): GraphQLResponseFlow<RepoDetailsQuery.Data>

    fun getRepoLicense(owner: String, name: String): GraphQLResponseFlow<RepoLicenseQuery.Data>

    fun prefetchRepoTree(owner: String, name: String): GraphQLResponseFlow<RepoTreePrefetchQuery.Data>

    fun getRepoIssues(
        owner: String,
        name: String,
        after: String? = null,
        states: Set<IssueState> = setOf(IssueState.OPEN)
    ): GraphQLResponseFlow<RepoIssuesQuery.Data>

    fun getRepoPullRequests(
        owner: String,
        name: String,
        after: String? = null,
        states: Set<PullRequestState> = setOf(PullRequestState.OPEN)
    ): GraphQLResponseFlow<RepoPullRequestsQuery.Data>

    fun getRepoReleases(
        owner: String,
        name: String,
        after: String? = null
    ): GraphQLResponseFlow<RepoReleasesQuery.Data>

    fun getRepoForks(
        owner: String,
        name: String,
        after: String? = null
    ): GraphQLResponseFlow<RepoForksQuery.Data>

    fun getRepoContributors(
        owner: String,
        name: String,
        after: String? = null
    ): GraphQLResponseFlow<RepoContributorsQuery.Data>

    fun getCommits(
        id: String,
        branch: String? = null,
        after: String? = null
    ): GraphQLResponseFlow<CommitsQuery.Data>

    // Release

    fun getReleaseDetails(
        owner: String,
        name: String,
        tag: String,
        after: String? = null
    ): GraphQLResponseFlow<ReleaseDetailsQuery.Data>

    // Files

    fun getTree(owner: String, name: String, branchAndPath: String): GraphQLResponseFlow<TreeQuery.Data>

    fun getFile(owner: String, name: String, branch: String, path: String): GraphQLResponseFlow<FileQuery.Data>

    fun getRawMarkdown(owner: String, name: String, branch: String, path: String): GraphQLResponseFlow<RawMarkdownQuery.Data>

}

internal class NetworkGraphQLDataSource(
    private val apolloClient: ApolloClient
): GraphQLDataSource {


    private fun <D: Operation.Data> ApolloCall<D>.addToken(): ApolloCall<D> {
        return addHttpHeader("Authorization", "") // TODO: Retrieve token from storage
    }

    // Account

    override fun getAccountInfo(token: String): GraphQLResponseFlow<AccountInfoQuery.Data> {
        return apolloClient.query(AccountInfoQuery())
            .doNotStore(true)
            .addHttpHeader("Authorization", token)
            .response()
    }

    override fun identify(): GraphQLResponseFlow<IdentifyQuery.Data> {
        return apolloClient.query(IdentifyQuery())
            .addToken()
            .response()
    }

    // Starrable

    override fun star(id: String): GraphQLResponseFlow<StarMutation.Data> {
        return apolloClient.mutation(StarMutation(id))
            .addToken()
            .response()
    }

    override fun unstar(id: String): GraphQLResponseFlow<UnstarMutation.Data> {
        return apolloClient.mutation(UnstarMutation(id))
            .addToken()
            .response()
    }

    // Reactable

    override fun react(
        id: String,
        reaction: ReactionContent
    ): GraphQLResponseFlow<ReactMutation.Data> {
        return apolloClient.mutation(ReactMutation(id, reaction))
            .addToken()
            .response()
    }

    override fun unreact(
        id: String,
        reaction: ReactionContent
    ): GraphQLResponseFlow<UnreactMutation.Data> {
        return apolloClient.mutation(UnreactMutation(id, reaction))
            .addToken()
            .response()
    }

    // Profile

    override fun getCurrentProfile(): GraphQLResponseFlow<ProfileQuery.Data> {
        return apolloClient.query(ProfileQuery())
            .addToken()
            .response()
    }

    override fun getProfile(login: String): GraphQLResponseFlow<UserProfileQuery.Data> {
        return apolloClient.query(UserProfileQuery(login))
            .addToken()
            .response()
    }

    override fun getUserRepositories(
        login: String,
        after: String?,
        count: Int
    ): GraphQLResponseFlow<RepoListQuery.Data> {
        return apolloClient.query(
            RepoListQuery(
                username = login,
                cursor = after.toOptional(),
                total = count.toOptional()
            )
        )
            .addToken()
            .response()
    }

    override fun getStarredRepositories(
        login: String,
        after: String?,
        count: Int
    ): GraphQLResponseFlow<StarredReposQuery.Data> {
        return apolloClient.query(
            StarredReposQuery(
                username = login,
                cursor = after.toOptional(),
                total = count.toOptional()
            )
        )
            .addToken()
            .response()
    }

    override fun getJoinedOrgs(
        login: String,
        after: String?,
        count: Int
    ): GraphQLResponseFlow<JoinedOrgsQuery.Data> {
        return apolloClient.query(
            JoinedOrgsQuery(
                username = login,
                cursor = after.toOptional(),
                total = count.toOptional()
            )
        )
            .addToken()
            .response()
    }

    override fun getFollowers(
        login: String,
        after: String?,
        count: Int
    ): GraphQLResponseFlow<FollowersQuery.Data> {
        return apolloClient.query(
            FollowersQuery(
                username = login,
                cursor = after.toOptional(),
                total = count.toOptional()
            )
        )
            .addToken()
            .response()
    }

    override fun getFollowing(
        login: String,
        after: String?,
        count: Int
    ): GraphQLResponseFlow<FollowingQuery.Data> {
        return apolloClient.query(
            FollowingQuery(
                username = login,
                cursor = after.toOptional(),
                total = count.toOptional()
            )
        )
            .addToken()
            .response()
    }

    override fun getSponsoring(
        login: String,
        after: String?,
        count: Int
    ): GraphQLResponseFlow<SponsoringQuery.Data> {
        return apolloClient.query(
            SponsoringQuery(
                username = login,
                cursor = after.toOptional(),
                total = count.toOptional()
            )
        )
            .addToken()
            .response()
    }

    override fun followUser(id: String): GraphQLResponseFlow<FollowUserMutation.Data> {
        return apolloClient.mutation(FollowUserMutation(id))
            .addToken()
            .response()
    }

    override fun unfollowUser(id: String): GraphQLResponseFlow<UnfollowUserMutation.Data> {
        return apolloClient.mutation(UnfollowUserMutation(id))
            .addToken()
            .response()
    }

    // Feed

    override fun getFeed(after: String?): GraphQLResponseFlow<FeedQuery.Data> {
        return apolloClient.query(FeedQuery(after.toOptional()))
            .addToken()
            .response()
    }

    override fun getTrending(period: TrendingPeriod): GraphQLResponseFlow<TrendingQuery.Data> {
        return apolloClient.query(TrendingQuery(period))
            .addToken()
            .response()
    }

    // Repositories

    override fun getRepoName(
        owner: String,
        name: String
    ): GraphQLResponseFlow<RepoNameQuery.Data> {
        return apolloClient.query(RepoNameQuery(owner, name))
            .addToken()
            .response()
    }

    override fun getRepoDetails(
        owner: String,
        name: String
    ): GraphQLResponseFlow<RepoDetailsQuery.Data> {
        return apolloClient.query(RepoDetailsQuery(owner, name))
            .addToken()
            .response()
    }

    override fun getRepoLicense(
        owner: String,
        name: String
    ): GraphQLResponseFlow<RepoLicenseQuery.Data> {
        return apolloClient.query(RepoLicenseQuery(owner, name))
            .addToken()
            .response()
    }

    override fun prefetchRepoTree(
        owner: String,
        name: String
    ): GraphQLResponseFlow<RepoTreePrefetchQuery.Data> {
        return apolloClient.query(RepoTreePrefetchQuery(owner, name))
            .addToken()
            .response()
    }

    override fun getRepoIssues(
        owner: String,
        name: String,
        after: String?,
        states: Set<IssueState>
    ): GraphQLResponseFlow<RepoIssuesQuery.Data> {
        return apolloClient.query(RepoIssuesQuery(owner, name, after.toOptional(), states.toList()))
            .addToken()
            .response()
    }

    override fun getRepoPullRequests(
        owner: String,
        name: String,
        after: String?,
        states: Set<PullRequestState>
    ): GraphQLResponseFlow<RepoPullRequestsQuery.Data> {
        return apolloClient.query(RepoPullRequestsQuery(owner, name, after.toOptional(), states.toList()))
            .addToken()
            .response()
    }

    override fun getRepoReleases(
        owner: String,
        name: String,
        after: String?
    ): GraphQLResponseFlow<RepoReleasesQuery.Data> {
        return apolloClient.query(RepoReleasesQuery(owner, name, after.toOptional()))
            .addToken()
            .response()
    }

    override fun getRepoForks(
        owner: String,
        name: String,
        after: String?
    ): GraphQLResponseFlow<RepoForksQuery.Data> {
        return apolloClient.query(RepoForksQuery(owner, name, after.toOptional()))
            .addToken()
            .response()
    }

    override fun getRepoContributors(
        owner: String,
        name: String,
        after: String?
    ): GraphQLResponseFlow<RepoContributorsQuery.Data> {
        return apolloClient.query(RepoContributorsQuery(owner, name, after.toOptional()))
            .addToken()
            .response()
    }

    override fun getCommits(
        id: String,
        branch: String?,
        after: String?
    ): GraphQLResponseFlow<CommitsQuery.Data> {
        return apolloClient.query(CommitsQuery(id, branch.toOptional(), after.toOptional()))
            .addToken()
            .response()
    }

    // Releases

    override fun getReleaseDetails(
        owner: String,
        name: String,
        tag: String,
        after: String?
    ): GraphQLResponseFlow<ReleaseDetailsQuery.Data> {
        return apolloClient.query(ReleaseDetailsQuery(owner, name, tag, after.toOptional()))
            .addToken()
            .response()
    }

    // Files

    override fun getTree(
        owner: String,
        name: String,
        branchAndPath: String
    ): GraphQLResponseFlow<TreeQuery.Data> {
        return apolloClient.query(TreeQuery(owner, name, branchAndPath))
            .addToken()
            .response()
    }

    override fun getFile(
        owner: String,
        name: String,
        branch: String,
        path: String
    ): GraphQLResponseFlow<FileQuery.Data> {
        return apolloClient.query(FileQuery(owner, name, branch, path))
            .addToken()
            .response()
    }

    override fun getRawMarkdown(
        owner: String,
        name: String,
        branch: String,
        path: String
    ): GraphQLResponseFlow<RawMarkdownQuery.Data> {
        return apolloClient.query(RawMarkdownQuery(owner, name, branch, path))
            .addToken()
            .response()
    }

}