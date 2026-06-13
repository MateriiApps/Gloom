package dev.materii.gloom.core.model.feed.item

import dev.materii.gloom.core.model.feed.entity.FeedActor
import dev.materii.gloom.core.model.feed.entity.FeedEntity
import dev.materii.gloom.core.model.feed.entity.FeedOrganization
import dev.materii.gloom.core.model.feed.entity.FeedUser
import kotlinx.datetime.Instant
import dev.materii.gloom.core.graphql.fragment.FollowRecommendationFeedItem as FollowRecommendationFeedItemFragment

/**
 * Feed item representing a user being recommended to follow.
 *
 * @param createdAt When this item appeared on the feed
 * @param user The user the current user should follow
 */
data class FollowRecommendationFeedItem(
    override val createdAt: Instant,
    val user: FeedEntity
): FeedItem {

    /**
     * Recommendations aren't actions and thus don't have an actor, so this
     * will always be null
     */
    override val actor: FeedActor? = null

    companion object {

        fun fromFragment(fragment: FollowRecommendationFeedItemFragment) = with(fragment) {
            FollowRecommendationFeedItem(
                createdAt = fragment.createdAt,
                user = if (followee.feedUser != null) {
                    FeedUser.fromFragment(followee.feedUser!!)
                } else {
                    FeedOrganization.fromFragment(followee.feedOrg!!)
                }
            )
        }

    }

}
