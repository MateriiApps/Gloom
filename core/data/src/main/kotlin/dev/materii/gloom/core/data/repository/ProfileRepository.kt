package dev.materii.gloom.core.data.repository

import dev.materii.gloom.core.common.api.graphql.GraphQLResponseFlow
import dev.materii.gloom.core.common.api.graphql.transform
import dev.materii.gloom.core.graphql.*
import dev.materii.gloom.core.graphql.fragment.UserProfile

interface ProfileRepository {

    /**
     * Retrieves the profile for the logged in user
     */
    fun getCurrentProfile(): GraphQLResponseFlow<UserProfile>

    /**
     * Retrieves the profile for a user or organization
     *
     * @param login The username of the user or organization
     *
     * @return The profile and a boolean indicating if they sponsor the project
     */
    fun getProfile(login: String): GraphQLResponseFlow<Pair<UserProfileQuery.RepositoryOwner?, Boolean>>

    /**
     * Get a list of repositories owned by a User or Org
     *
     * @param login The username of the user or organization
     * @param after The key to use to get the next page
     * @param count Number of repositories to return
     */
    fun getUserRepositories(login: String, after: String? = null, count: Int = 30): GraphQLResponseFlow<RepoListQuery.Repositories?>

    /**
     * Get a list of repositories starred by a User
     *
     * @param login The username of the user
     * @param after The key to use to get the next page
     * @param count Number of repositories to return
     */
    fun getStarredRepositories(
        login: String,
        after: String? = null,
        count: Int = 30
    ): GraphQLResponseFlow<StarredReposQuery.StarredRepositories?>

    /**
     * Get a list of organizations a User is a member of
     *
     * @param login The username of the user
     * @param after The key to use to get the next page
     * @param count Number of organizations to return
     */
    fun getJoinedOrgs(
        login: String,
        after: String? = null,
        count: Int = 30
    ): GraphQLResponseFlow<JoinedOrgsQuery.Organizations?>

    /**
     * Get a list of users following a User or Org
     *
     * @param login The username of the user
     * @param after The key to use to get the next page
     * @param count Number of users to return
     */
    fun getFollowers(
        login: String,
        after: String? = null,
        count: Int = 30
    ): GraphQLResponseFlow<FollowersQuery.Followers?>

    /**
     * Get a list of users followed by a User or Org
     *
     * @param login The username of the user
     * @param after The key to use to get the next page
     * @param count Number of users to return
     */
    fun getFollowing(
        login: String,
        after: String? = null,
        count: Int = 30
    ): GraphQLResponseFlow<FollowingQuery.Following?>

    /**
     * Get a list of users being sponsored by a User or Org
     *
     * @param login The username of the user
     * @param after The key to use to get the next page
     * @param count Number of users to return
     */
    fun getSponsoring(
        login: String,
        after: String? = null,
        count: Int = 30
    ): GraphQLResponseFlow<SponsoringQuery.RepositoryOwner?>

    /**
     * Follow a User
     *
     * @param id The user's ID
     */
    fun followUser(id: String): GraphQLResponseFlow<FollowUserMutation.User?>

    /**
     * Unfollow a User
     *
     * @param id The user's ID
     */
    fun unfollowUser(id: String): GraphQLResponseFlow<UnfollowUserMutation.User?>

}

internal class ProfileRepositoryImpl(
    private val graphQL: GraphQLDataSource
): ProfileRepository {

    override fun getCurrentProfile(): GraphQLResponseFlow<UserProfile> {
        return graphQL.getCurrentProfile().transform {
            it.viewer.userProfile
        }
    }

    override fun getProfile(login: String): GraphQLResponseFlow<Pair<UserProfileQuery.RepositoryOwner?, Boolean>> {
        return graphQL.getProfile(login).transform {
            it.repositoryOwner to (it.user?.isSponsoredBy ?: false)
        }
    }

    override fun getUserRepositories(login: String, after: String?, count: Int): GraphQLResponseFlow<RepoListQuery.Repositories?> {
        return graphQL.getUserRepositories(login, after, count).transform {
            it.repositoryOwner?.repositories
        }
    }

    override fun getStarredRepositories(
        login: String,
        after: String?,
        count: Int
    ): GraphQLResponseFlow<StarredReposQuery.StarredRepositories?> {
        return graphQL.getStarredRepositories(login, after, count).transform {
            it.user?.starredRepositories
        }
    }

    override fun getJoinedOrgs(login: String, after: String?, count: Int): GraphQLResponseFlow<JoinedOrgsQuery.Organizations?> {
        return graphQL.getJoinedOrgs(login, after, count).transform {
            it.user?.organizations
        }
    }

    override fun getFollowers(login: String, after: String?, count: Int): GraphQLResponseFlow<FollowersQuery.Followers?> {
        return graphQL.getFollowers(login, after, count).transform {
            it.user?.followers
        }
    }

    override fun getFollowing(login: String, after: String?, count: Int): GraphQLResponseFlow<FollowingQuery.Following?> {
        return graphQL.getFollowing(login, after, count).transform {
            it.user?.following
        }
    }

    override fun getSponsoring(login: String, after: String?, count: Int): GraphQLResponseFlow<SponsoringQuery.RepositoryOwner?> {
        return graphQL.getSponsoring(login, after, count).transform {
            it.repositoryOwner
        }
    }

    override fun followUser(id: String): GraphQLResponseFlow<FollowUserMutation.User?> {
        return graphQL.followUser(id).transform {
            it.followUser?.user
        }
    }

    override fun unfollowUser(id: String): GraphQLResponseFlow<UnfollowUserMutation.User?> {
        return graphQL.unfollowUser(id).transform {
            it.unfollowUser?.user
        }
    }

}