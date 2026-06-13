package dev.materii.gloom.core.model.feed.entity

import dev.materii.gloom.core.graphql.fragment.FeedOrg

/**
 * An organization that can appear in the feed.
 *
 * @param id The org's id
 * @param login The org's username
 * @param displayName The name of the organization
 * @param avatarUrl The icon url for the organization
 * @param bio The org's description
 * @param isCurrentUserFollowing Whether or not the signed in user is following this org
 * @param counts Holds the number of repositories under the org
 */
data class FeedOrganization(
    override val id: String,
    override val login: String,
    override val displayName: String?,
    override val avatarUrl: String,
    override val bio: String?,
    override val isCurrentUserFollowing: Boolean,
    override val counts: Counts
): FeedEntity {

    companion object {

        fun fromFragment(fragment: FeedOrg) = with(fragment) {
            FeedOrganization(
                id = id,
                login = login,
                displayName = name,
                avatarUrl = avatarUrl,
                bio = description,
                isCurrentUserFollowing = viewerIsFollowing,
                counts = Counts(
                    repositories = repositories.totalCount
                )
            )
        }

    }

}