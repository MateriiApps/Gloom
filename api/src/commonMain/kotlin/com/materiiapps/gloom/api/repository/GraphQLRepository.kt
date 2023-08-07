package com.materiiapps.gloom.api.repository

import com.materiiapps.gloom.api.models.ModelUser
import com.materiiapps.gloom.api.service.GraphQLService
import com.materiiapps.gloom.api.utils.transform
import com.materiiapps.gloom.gql.type.IssueState
import com.materiiapps.gloom.gql.type.PullRequestState
import com.materiiapps.gloom.gql.type.ReactionContent

class GraphQLRepository(
    private val service: GraphQLService
) {

    suspend fun getCurrentProfile() =
        service.getCurrentProfile().transform { ModelUser.fromProfileQuery(it) }

    suspend fun getProfile(username: String) =
        service.getProfile(username).transform { ModelUser.fromUserProfileQuery(it) }

    suspend fun getRepositoriesForUser(
        username: String,
        after: String? = null,
        count: Int? = null
    ) = service.getRepositoriesForUser(username, after, count)

    suspend fun getStarredRepositories(
        username: String,
        after: String? = null,
        count: Int? = null
    ) = service.getStarredRepositories(username, after, count)

    suspend fun getJoinedOrgs(
        username: String,
        after: String? = null,
        count: Int? = null
    ) = service.getJoinedOrgs(username, after, count)

    suspend fun getFollowers(
        username: String,
        after: String? = null,
        count: Int? = null
    ) = service.getFollowers(username, after, count)

    suspend fun getFollowing(
        username: String,
        after: String? = null,
        count: Int? = null
    ) = service.getFollowing(username, after, count)

    suspend fun getSponsoring(
        username: String,
        after: String? = null,
        count: Int? = null
    ) = service.getSponsoring(username, after, count)

    suspend fun followUser(id: String) =
        service.followUser(id).transform {
            (it.followUser?.user?.viewerIsFollowing
                ?: false) to (it.followUser?.user?.followers?.totalCount ?: 0)
        }

    suspend fun unfollowUser(id: String) =
        service.unfollowUser(id).transform {
            (it.unfollowUser?.user?.viewerIsFollowing
                ?: false) to (it.unfollowUser?.user?.followers?.totalCount ?: 0)
        }

    suspend fun getFeed(cursor: String? = null) = service.getFeed(cursor)

    suspend fun starRepo(id: String) = service.starRepo(id).transform {
        (it.addStar?.starrable?.viewerHasStarred
            ?: false) to (it.addStar?.starrable?.stargazers?.totalCount ?: 0)
    }

    suspend fun unstarRepo(id: String) = service.unstarRepo(id).transform {
        (it.removeStar?.starrable?.viewerHasStarred
            ?: false) to (it.removeStar?.starrable?.stargazers?.totalCount ?: 0)
    }

    suspend fun identify() = service.identify().transform { it.viewer }

    suspend fun getRepoOverview(owner: String, name: String) =
        service.getRepoName(owner, name).transform { it.repository?.repoOverview }

    suspend fun getRepoDetails(owner: String, name: String) =
        service.getRepoDetails(owner, name).transform { it.repository?.repoDetails }

    suspend fun getRepoFiles(owner: String, name: String, branchAndPath: String) =
        service.getRepoFiles(owner, name, branchAndPath).transform {
            it.repository?.gitObject?.treeFragment?.entries?.map { entry -> entry.fileEntryFragment }
                ?: emptyList()
        }

    suspend fun getDefaultBranch(owner: String, name: String) =
        service.getDefaultBranch(owner, name).transform {
            it.repository?.defaultBranchRef?.name
        }

    suspend fun getRepoIssues(
        owner: String,
        name: String,
        after: String? = null,
        states: List<IssueState> = listOf(IssueState.OPEN, IssueState.CLOSED)
    ) = service.getRepoIssues(owner, name, after, states)

    suspend fun getRepoPullRequests(
        owner: String,
        name: String,
        after: String? = null,
        states: List<PullRequestState> = listOf(PullRequestState.OPEN)
    ) = service.getRepoPullRequests(owner, name, after, states)

    suspend fun getRepoReleases(
        owner: String,
        name: String,
        after: String? = null
    ) = service.getRepoReleases(owner, name, after)

    suspend fun getReleaseDetails(
        owner: String,
        name: String,
        tag: String,
        after: String? = null
    ) = service.getReleaseDetails(owner, name, tag, after)

    suspend fun react(id: String, reaction: ReactionContent) = service.react(id, reaction)

    suspend fun unreact(id: String, reaction: ReactionContent) = service.unreact(id, reaction)

}