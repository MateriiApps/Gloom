package dev.materii.gloom.core.model.feed.entity

/**
 * Various statistics than can be viewed in the feed
 *
 * @param repositories Number of repositories owned by a user or org
 * @param followers Number of users following a user
 * @param stars Number of stars on a repository
 * @param contributors Number of users that contributed to a repository
 * @param commits Number of commits to a repository or included in a pull request
 */
data class Counts(
    val repositories: Int = 0,
    val followers: Int = 0,
    val stars: Int = 0,
    val contributors: Int = 0,
    val commits: Int = 0
)