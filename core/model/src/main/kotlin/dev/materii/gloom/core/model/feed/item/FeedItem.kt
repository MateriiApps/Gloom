package dev.materii.gloom.core.model.feed.item

import dev.materii.gloom.core.graphql.FeedQuery
import dev.materii.gloom.core.model.feed.entity.FeedActor
import kotlinx.datetime.Instant

/**
 * An item that can appear in a user's feed.
 */
interface FeedItem {

    val actor: FeedActor?
    val createdAt: Instant

    companion object {

        fun fromFragment(fragment: FeedQuery.Node) = with(fragment) {
            when {
                createdRepoFeedItem != null -> CreatedRepoFeedItem.fromFragment(createdRepoFeedItem!!)
                followedUserFeedItem != null -> FollowedUserFeedItem.fromFragment(followedUserFeedItem!!)
                followRecommendationFeedItem != null -> FollowRecommendationFeedItem.fromFragment(followRecommendationFeedItem!!)
                forkedRepositoryFeedItem != null -> ForkedRepositoryFeedItem.fromFragment(forkedRepositoryFeedItem!!)
                mergedPullRequestFeedItem != null -> MergedPullRequestFeedItem.fromFragment(mergedPullRequestFeedItem!!)
                newReleaseFeedItem != null -> NewReleaseFeedItem.fromFragment(newReleaseFeedItem!!)
                recommendedRepositoryFeedItem != null -> RepositoryRecommendationFeedItem.fromFragment(recommendedRepositoryFeedItem!!)
                starredRepoFeedItem != null -> StarredRepoFeedItem.fromFragment(starredRepoFeedItem!!)
                else -> throw IllegalArgumentException("Node does not contain any supported feed items")
            }
        }

    }
}