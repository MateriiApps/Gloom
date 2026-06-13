package dev.materii.gloom.core.model.feed.item

import dev.materii.gloom.core.graphql.fragment.RecommendedRepositoryFeedItem
import dev.materii.gloom.core.model.feed.entity.FeedActor
import dev.materii.gloom.core.model.feed.entity.FeedRepository
import kotlinx.datetime.Instant

/**
 * Feed item representing a repository being recommended.
 *
 * @param createdAt When this feed item was shown
 * @param repository The repository being recommended
 */
data class RepositoryRecommendationFeedItem(
    override val createdAt: Instant,
    val repository: FeedRepository
): FeedItem {

    /**
     * Recommendations aren't actions and thus don't have an actor, so this
     * will always be null
     */
    override val actor: FeedActor? = null

    companion object {

        fun fromFragment(fragment: RecommendedRepositoryFeedItem) = with(fragment) {
            RepositoryRecommendationFeedItem(
                createdAt = createdAt,
                repository = FeedRepository.fromFragment(repository.feedRepository)
            )
        }

    }

}
