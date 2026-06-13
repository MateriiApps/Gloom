package dev.materii.gloom.core.model.account

import dev.materii.gloom.core.graphql.fragment.UserAccount as UserAccountFragment

/**
 * Account details for a logged in user.
 *
 * @param id The user's id
 * @param login The username for the account
 * @param displayName Optional display name for the user
 * @param avatarUrl Url to the account's avatar
 * @param unreadNotificationCount Number of notifications not yet read
 */
data class UserAccount(
    val id: String,
    val login: String,
    val displayName: String?,
    val avatarUrl: String,
    val unreadNotificationCount: Int
) {

    companion object {

        fun fromFragment(fragment: UserAccountFragment) = with(fragment) {
            UserAccount(
                id = id,
                login = login,
                displayName = name,
                avatarUrl = avatarUrl,
                unreadNotificationCount = notificationListsWithThreadCount.totalCount
            )
        }

    }

}