package dev.materii.gloom.core.model.feed.item

import dev.materii.gloom.core.model.feed.entity.FeedActor
import dev.materii.gloom.core.model.feed.entity.FeedRepository
import kotlinx.datetime.Instant
import dev.materii.gloom.core.graphql.fragment.StarredRepoFeedItem as StarredRepoFeedItemFragment

/**
 * Feed item representing a repository being starred.
 *
 * @param actor The user starring the repository
 * @param createdAt When the repository was starred
 * @param repository The repository being starred
 */
data class StarredRepoFeedItem(
    override val actor: FeedActor,
    override val createdAt: Instant,
    val repository: FeedRepository
): FeedItem {

    companion object {

        fun fromFragment(fragment: StarredRepoFeedItemFragment) = with(fragment) {
            StarredRepoFeedItem(
                actor = FeedActor.fromFragment(actor.feedActor),
                createdAt = createdAt,
                repository = FeedRepository.fromFragment(repository.feedRepository)
            )
        }

    }

}
