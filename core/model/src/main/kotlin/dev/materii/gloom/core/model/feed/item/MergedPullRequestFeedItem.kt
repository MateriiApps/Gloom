package dev.materii.gloom.core.model.feed.item

import dev.materii.gloom.core.model.feed.entity.FeedActor
import dev.materii.gloom.core.model.feed.entity.FeedPullRequest
import kotlinx.datetime.Instant
import dev.materii.gloom.core.graphql.fragment.MergedPullRequestFeedItem as MergedPullRequestFeedItemFragment

/**
 * Feed item representing a successfully merged pull request.
 *
 * @param actor The author of the pull request
 * @param createdAt When the pull request was merged
 * @param pullRequest The merged pull request
 */
data class MergedPullRequestFeedItem(
    override val actor: FeedActor,
    override val createdAt: Instant,
    val pullRequest: FeedPullRequest
): FeedItem {

    companion object {

        fun fromFragment(fragment: MergedPullRequestFeedItemFragment) = with(fragment) {
            MergedPullRequestFeedItem(
                actor = FeedActor.fromFragment(actor.feedActor),
                createdAt = createdAt,
                pullRequest = FeedPullRequest.fromFragment(pullRequest.feedPullRequest)
            )
        }

    }

}