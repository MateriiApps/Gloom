package dev.materii.gloom.core.model.feed.item

import dev.materii.gloom.core.model.feed.entity.FeedActor
import dev.materii.gloom.core.model.feed.entity.FeedEntity
import dev.materii.gloom.core.model.feed.entity.FeedOrganization
import dev.materii.gloom.core.model.feed.entity.FeedUser
import kotlinx.datetime.Instant
import dev.materii.gloom.core.graphql.fragment.FollowedUserFeedItem as FollowedUserFeedItemFragment

/**
 * Feed item representing a user being followed.
 *
 * @param actor The followee
 * @param createdAt When the follow action appeared in the feed
 * @param user The user being followed
 */
data class FollowedUserFeedItem(
    override val actor: FeedActor,
    override val createdAt: Instant,
    val user: FeedEntity
): FeedItem {

    companion object {

        fun fromFragment(fragment: FollowedUserFeedItemFragment) = with(fragment) {
            FollowedUserFeedItem(
                actor = FeedActor.fromFragment(follower.feedActor),
                createdAt = createdAt,
                user = if (followee.feedUser != null) {
                    FeedUser.fromFragment(followee.feedUser!!)
                } else {
                    FeedOrganization.fromFragment(followee.feedOrg!!)
                }
            )
        }

    }

}
