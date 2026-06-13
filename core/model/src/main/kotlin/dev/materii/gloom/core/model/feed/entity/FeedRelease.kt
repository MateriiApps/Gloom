package dev.materii.gloom.core.model.feed.entity

import dev.materii.gloom.core.graphql.fragment.FeedRelease as FeedReleaseFragment

/**
 * A release that can appear in the feed.
 *
 * @param name The name of the release, may match the [tag]
 * @param description Description given to this release
 * @param tag Name of the Git tag associated with this release
 * @param isLatest Whether or not this is the latest non-pre-release release
 * @param repository Repository this release was created for
 */
data class FeedRelease(
    val name: String,
    val description: String?,
    val tag: String,
    val isLatest: Boolean,
    val commitHash: String?,
    val repository: FeedRepository
) {

    companion object {

        fun fromFragment(fragment: FeedReleaseFragment) = with(fragment) {
            FeedRelease(
                name = if (name.isNullOrBlank()) tagName else name!!,
                description = descriptionHTML,
                tag = tagName,
                isLatest = isLatest,
                commitHash = tagCommit?.abbreviatedOid,
                repository = FeedRepository.fromFragment(repository.feedRepository)
            )
        }

    }

}
