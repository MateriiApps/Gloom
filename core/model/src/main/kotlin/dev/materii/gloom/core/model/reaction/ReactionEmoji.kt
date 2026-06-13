package dev.materii.gloom.core.model.reaction

import dev.materii.gloom.core.graphql.type.ReactionContent

enum class ReactionEmoji(
    val emoji: String,
    val canReactToRelease: Boolean
) {

    UNKNOWN("❓", false),
    THUMBS_UP("👍", true),
    THUMBS_DOWN("👎", false),
    LAUGH("😄", true),
    HOORAY("🎉", true),
    CONFUSED("😕", false),
    HEART("♥", true),
    ROCKET("🚀", true),
    EYES("👀", true);

    companion object {

        val all get() = entries.filterNot { it == UNKNOWN }
        val release get() = all.filter { it.canReactToRelease }

        fun fromType(type: ReactionContent) = with(type) {
            when (this) {
                ReactionContent.THUMBS_UP -> THUMBS_UP
                ReactionContent.THUMBS_DOWN -> THUMBS_DOWN
                ReactionContent.LAUGH -> LAUGH
                ReactionContent.HOORAY -> HOORAY
                ReactionContent.CONFUSED -> CONFUSED
                ReactionContent.HEART -> HEART
                ReactionContent.ROCKET -> ROCKET
                ReactionContent.EYES -> EYES
                else -> UNKNOWN
            }
        }

    }

}