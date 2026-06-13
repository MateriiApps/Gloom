package dev.materii.gloom.core.model.feed.entity

import dev.materii.gloom.core.graphql.fragment.FeedUser as FeedUserFragment

/**
 * A user that can appear in the feed.
 *
 * @param id The user's id
 * @param login The user's username
 * @param displayName The user's display name, if set
 * @param avatarUrl Link to the user's avatar
 * @param bio The user's bio, if set
 * @param counts Holds the number of followers and repositories
 * @param isCurrentUserFollowing Whether or not the logged in user is following this user
 * @param isFollowingCurrentUser Whether or not this user is following the logged in user
 * @param isCurrentUser Whether or not this user is the logged in user
 */
data class FeedUser(
    override val id: String,
    override val login: String,
    override val displayName: String?,
    override val avatarUrl: String,
    override val bio: String?,
    override val counts: Counts,
    override val isCurrentUserFollowing: Boolean,
    val isFollowingCurrentUser: Boolean,
    val isCurrentUser: Boolean
): FeedEntity {

    companion object {

        fun fromFragment(fragment: FeedUserFragment) = with(fragment) {
            FeedUser(
                id = id,
                login = login,
                displayName = name,
                avatarUrl = avatarUrl,
                bio = bio,
                isCurrentUserFollowing = viewerIsFollowing,
                isFollowingCurrentUser = isFollowingViewer,
                isCurrentUser = isViewer,
                counts = Counts(
                    repositories = repositories.totalCount,
                    followers = followers.totalCount
                )
            )
        }

    }

}