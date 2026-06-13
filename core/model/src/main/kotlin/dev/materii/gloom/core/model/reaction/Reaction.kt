package dev.materii.gloom.core.model.reaction

import dev.materii.gloom.core.graphql.fragment.Reaction as ReactionFragment

/**
 * A reaction that can appear on a comment, pull request, or a release.
 *
 * @param emoji The emoji being reacted with
 * @param currentUserReacted Whether or not the current user has reacted with this
 * @param reactionCount Total number of users reacting with this
 */
data class Reaction(
    val emoji: ReactionEmoji,
    val currentUserReacted: Boolean,
    val reactionCount: Int
) {

    companion object {

        fun fromFragment(fragment: ReactionFragment) = with(fragment) {
            Reaction(
                emoji = ReactionEmoji.fromType(content),
                currentUserReacted = viewerHasReacted,
                reactionCount = reactors.totalCount
            )
        }

    }

}
