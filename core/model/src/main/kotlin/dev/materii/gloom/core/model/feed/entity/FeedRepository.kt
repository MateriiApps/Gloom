package dev.materii.gloom.core.model.feed.entity

import dev.materii.gloom.core.model.language.Language
import dev.materii.gloom.core.graphql.fragment.FeedRepository as FeedRepositoryFragment

/**
 * A repository that can appear in the feed.
 *
 * @param id The id of the repository
 * @param name The name of the repository
 * @param description Brief description of the library
 * @param owner The user or org that owns the repository
 * @param primaryLanguage The programming language primarily used in the repository
 * @param openGraphImageUrl Image used when this repository is externally linked to
 * @param currentUserStarred Whether or not the logged in user has starred this repository
 * @param counts Holds the number of stars and contributors
 */
data class FeedRepository(
    val id: String,
    val name: String,
    val description: String?,
    val owner: FeedActor,
    val primaryLanguage: Language?,
    val openGraphImageUrl: String,
    val currentUserStarred: Boolean,
    val counts: Counts
) {

    companion object {

        fun fromFragment(fragment: FeedRepositoryFragment) = with(fragment) {
            FeedRepository(
                id = id,
                name = name,
                description = description,
                owner = FeedActor(owner.login, owner.avatarUrl, owner.__typename == "Organization"),
                primaryLanguage = primaryLanguage?.let { Language(it.name, it.color) },
                openGraphImageUrl = openGraphImageUrl,
                currentUserStarred = viewerHasStarred,
                counts = Counts(
                    stars = stargazerCount,
                    contributors = contributorsCount
                )
            )
        }

    }

}
