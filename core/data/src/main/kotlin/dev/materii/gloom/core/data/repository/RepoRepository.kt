package dev.materii.gloom.core.data.repository

import dev.materii.gloom.core.common.api.graphql.GraphQLResponseFlow
import dev.materii.gloom.core.common.api.graphql.transform
import dev.materii.gloom.core.graphql.*
import dev.materii.gloom.core.graphql.fragment.RepoDetails
import dev.materii.gloom.core.graphql.fragment.RepoLicense
import dev.materii.gloom.core.graphql.fragment.RepoOverview
import dev.materii.gloom.core.graphql.type.IssueState
import dev.materii.gloom.core.graphql.type.PullRequestState

interface RepoRepository {

    /**
     * Get basic metadata for a repository
     *
     * @param owner Owner of the repository
     * @param name Name of the repository
     */
    fun getRepoName(owner: String, name: String): GraphQLResponseFlow<RepoOverview?>

    /**
     * Get the details for a particular repository
     *
     * @param owner Owner of the repository
     * @param name Name of the repository
     */
    fun getRepoDetails(owner: String, name: String): GraphQLResponseFlow<RepoDetails?>

    /**
     * Get license info for a repository
     *
     * @param owner Owner of the repository
     * @param name Name of the repository
     */
    fun getRepoLicense(owner: String, name: String): GraphQLResponseFlow<RepoLicense?>

    /**
     * Get metadata needed to fetch a repository's file tree
     *
     * @param owner Owner of the repository
     * @param name Name of the repository
     */
    fun prefetchRepoTree(owner: String, name: String): GraphQLResponseFlow<RepoTreePrefetchQuery.Repository?>

    /**
     * Get the issues made in a repository
     *
     * @param owner Owner of the repository
     * @param name Name of the repository
     * @param after Cursor used to get the next page of issues
     * @param states States to filter with
     */
    fun getRepoIssues(
        owner: String,
        name: String,
        after: String? = null,
        states: Set<IssueState> = setOf(IssueState.OPEN)
    ): GraphQLResponseFlow<RepoIssuesQuery.Issues?>

    /**
     * Get the pull requests made to a repository
     *
     * @param owner Owner of the repository
     * @param name Name of the repository
     * @param after Cursor used to get the next page of pull requests
     * @param states States to filter with
     */
    fun getRepoPullRequests(
        owner: String,
        name: String,
        after: String? = null,
        states: Set<PullRequestState> = setOf(PullRequestState.OPEN)
    ): GraphQLResponseFlow<RepoPullRequestsQuery.PullRequests?>

    /**
     * Get the releases from a repository
     *
     * @param owner Owner of the repository
     * @param name Name of the repository
     * @param after Cursor used to get the next page of releases
     */
    fun getRepoReleases(
        owner: String,
        name: String,
        after: String? = null
    ): GraphQLResponseFlow<RepoReleasesQuery.Releases?>

    /**
     * Get the forks made from a repository
     *
     * @param owner Owner of the repository
     * @param name Name of the repository
     * @param after Cursor used to get the next page of forks
     */
    fun getRepoForks(
        owner: String,
        name: String,
        after: String? = null
    ): GraphQLResponseFlow<RepoForksQuery.Forks?>

    /**
     * Get the contributors to a repository
     *
     * @param owner Owner of the repository
     * @param name Name of the repository
     * @param after Cursor used to get the next page of contributors
     */
    fun getRepoContributors(
        owner: String,
        name: String,
        after: String? = null
    ): GraphQLResponseFlow<RepoContributorsQuery.Contributors?>

    /**
     * Get the commits to a repository branch
     *
     * @param id Id of the repository
     * @param branch Branch to view commits from
     * @param after Cursor used to get the next page of commits
     */
    fun getCommits(
        id: String,
        branch: String,
        after: String? = null,
    ): GraphQLResponseFlow<CommitsQuery.History?>

    /**
     * Star a repository
     */
    fun star(id: String): GraphQLResponseFlow<StarMutation.AddStar?>

    /**
     * Unstar a repository
     */
    fun unstar(id: String): GraphQLResponseFlow<UnstarMutation.RemoveStar?>

}

internal class RepoRepositoryImpl(
    private val graphQL: GraphQLDataSource
): RepoRepository {

    override fun getRepoName(
        owner: String,
        name: String
    ): GraphQLResponseFlow<RepoOverview?> {
        return graphQL.getRepoName(owner, name).transform {
            it.repository?.repoOverview
        }
    }

    override fun getRepoDetails(
        owner: String,
        name: String
    ): GraphQLResponseFlow<RepoDetails?> {
        return graphQL.getRepoDetails(owner, name).transform {
            it.repository?.repoDetails
        }
    }

    override fun getRepoLicense(
        owner: String,
        name: String
    ): GraphQLResponseFlow<RepoLicense?> {
        return graphQL.getRepoLicense(owner, name).transform {
            it.repository?.licenseInfo?.repoLicense
        }
    }

    override fun prefetchRepoTree(
        owner: String,
        name: String
    ): GraphQLResponseFlow<RepoTreePrefetchQuery.Repository?> {
        return graphQL.prefetchRepoTree(owner, name).transform {
            it.repository
        }
    }

    override fun getRepoIssues(
        owner: String,
        name: String,
        after: String?,
        states: Set<IssueState>
    ): GraphQLResponseFlow<RepoIssuesQuery.Issues?> {
        return graphQL.getRepoIssues(owner, name, after, states).transform {
            it.repository?.issues
        }
    }

    override fun getRepoPullRequests(
        owner: String,
        name: String,
        after: String?,
        states: Set<PullRequestState>
    ): GraphQLResponseFlow<RepoPullRequestsQuery.PullRequests?> {
        return graphQL.getRepoPullRequests(owner, name, after, states).transform {
            it.repository?.pullRequests
        }
    }

    override fun getRepoReleases(
        owner: String,
        name: String,
        after: String?
    ): GraphQLResponseFlow<RepoReleasesQuery.Releases?> {
        return graphQL.getRepoReleases(owner, name, after).transform {
            it.repository?.releases
        }
    }

    override fun getRepoForks(
        owner: String,
        name: String,
        after: String?
    ): GraphQLResponseFlow<RepoForksQuery.Forks?> {
        return graphQL.getRepoForks(owner, name, after).transform {
            it.repository?.forks
        }
    }

    override fun getRepoContributors(
        owner: String,
        name: String,
        after: String?
    ): GraphQLResponseFlow<RepoContributorsQuery.Contributors?> {
        return graphQL.getRepoContributors(owner, name, after).transform {
            it.repository?.contributors
        }
    }

    override fun getCommits(
        id: String,
        branch: String,
        after: String?
    ): GraphQLResponseFlow<CommitsQuery.History?> {
        return graphQL.getCommits(id, branch, after).transform {
            it.node?.onRepository?.gitObject?.onCommit?.history
        }
    }

    override fun star(id: String): GraphQLResponseFlow<StarMutation.AddStar?> {
        return graphQL.star(id).transform {
            it.addStar
        }
    }

    override fun unstar(id: String): GraphQLResponseFlow<UnstarMutation.RemoveStar?> {
        return graphQL.unstar(id).transform {
            it.removeStar
        }
    }

}