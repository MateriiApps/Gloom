package dev.materii.gloom.core.model.feed.item

import dev.materii.gloom.core.model.feed.entity.FeedActor
import dev.materii.gloom.core.model.feed.entity.FeedRepository
import kotlinx.datetime.Instant
import dev.materii.gloom.core.graphql.fragment.CreatedRepoFeedItem as CreatedRepoFeedItemFragment

/**
 * Feed item representing a repository being created.
 *
 * Note that the actor and repository owner can differ,
 * most often when the repository was created in an org.
 *
 * @param actor The user that created the repository
 * @param createdAt Timestamp for when this feed item was added to the feed
 * @param repository The created repository
 */
data class CreatedRepoFeedItem(
    override val actor: FeedActor,
    override val createdAt: Instant,
    val repository: FeedRepository
): FeedItem {

    companion object {

        fun fromFragment(fragment: CreatedRepoFeedItemFragment) = with(fragment) {
            CreatedRepoFeedItem(
                actor = FeedActor.fromFragment(actor.feedActor),
                createdAt = createdAt,
                repository = FeedRepository.fromFragment(repository.feedRepository)
            )
        }

    }

}
