package dev.materii.gloom.core.model.feed.entity

import dev.materii.gloom.core.model.reaction.Reaction
import dev.materii.gloom.core.graphql.fragment.FeedPullRequest as FeedPullRequestFragment

/**
 * A pull request that can appear in a feed.
 *
 * @param id The id associated with the pull request
 * @param title The title given to the pull request
 * @param number The issue number given to the pull request
 * @param body Details describing the pull request (MD/HTML)
 * @param headRef Usually the branch requesting to be merged
 * @param baseRef Usually the branch being merged into
 * @param reactions All reactions to this pull request
 * @param mergedBy The username of the user that merged this pull request
 * @param baseRepository The repository hosting the [baseRef]
 * @param counts Holds the number of commits in this pull request
 */
data class FeedPullRequest(
    val id: String,
    val title: String,
    val number: Int,
    val body: String,
    val headRef: String,
    val baseRef: String,
    val reactions: List<Reaction>,
    val mergedBy: String,
    val baseRepository: BaseRepo?,
    val counts: Counts
) {

    // TODO: Extract elsewhere
    data class BaseRepo(
        val name: String,
        val ownerLogin: String,
        val ownerAvatarUrl: String,
        val ownerIsOrg: Boolean
    )

    companion object {

        fun fromFragment(fragment: FeedPullRequestFragment) = with(fragment) {
            FeedPullRequest(
                id = id,
                title = title,
                number = number,
                body = bodyHTML,
                headRef = headRefName,
                baseRef = baseRefName,
                reactions = reactionGroups.orEmpty().map { Reaction.fromFragment(it.reaction) },
                mergedBy = mergedBy?.login ?: "ghost",
                counts = Counts(commits = commits.totalCount),
                baseRepository = baseRepository?.let {
                    BaseRepo(
                        name = it.name,
                        ownerLogin = it.owner.login,
                        ownerAvatarUrl = it.owner.avatarUrl,
                        ownerIsOrg = it.owner.__typename == "Organization"
                    )
                }
            )
        }

    }

}
