package dev.materii.gloom.core.model.feed.item

import dev.materii.gloom.core.model.feed.entity.FeedActor
import dev.materii.gloom.core.model.feed.entity.FeedRelease
import kotlinx.datetime.Instant
import dev.materii.gloom.core.graphql.fragment.NewReleaseFeedItem as NewReleaseFeedItemFragment

/**
 * Feed item representing a published release.
 *
 * @param actor The user that created the release
 * @param createdAt When the release was created
 * @param release The release being published
 */
data class NewReleaseFeedItem(
    override val actor: FeedActor,
    override val createdAt: Instant,
    val release: FeedRelease
): FeedItem {

    companion object {

        fun fromFragment(fragment: NewReleaseFeedItemFragment) = with(fragment) {
            NewReleaseFeedItem(
                actor = FeedActor.fromFragment(actor.feedActor),
                createdAt = createdAt,
                release = FeedRelease.fromFragment(release.feedRelease)
            )
        }

    }

}