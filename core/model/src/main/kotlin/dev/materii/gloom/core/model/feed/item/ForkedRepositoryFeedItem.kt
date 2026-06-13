package dev.materii.gloom.core.model.feed.item

import dev.materii.gloom.core.model.feed.entity.FeedActor
import dev.materii.gloom.core.model.feed.entity.FeedRepository
import kotlinx.datetime.Instant
import dev.materii.gloom.core.graphql.fragment.ForkedRepositoryFeedItem as ForkedRepositoryFeedItemFragment

/**
 * Feed item representing a repository being forked.
 *
 * Note that the actor and repository owner can differ,
 * most often when the repository was created in an org.
 *
 * @param actor The user that created the fork
 * @param fork The resulting fork
 */
data class ForkedRepositoryFeedItem(
    override val actor: FeedActor,
    override val createdAt: Instant,
    val fork: FeedRepository
): FeedItem {

    companion object {

        fun fromFragment(fragment: ForkedRepositoryFeedItemFragment) = with(fragment) {
            ForkedRepositoryFeedItem(
                actor = FeedActor.fromFragment(actor.feedActor),
                createdAt = createdAt,
                fork = FeedRepository.fromFragment(repository.feedRepository)
            )
        }

    }

}
