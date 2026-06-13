package dev.materii.gloom.core.model.feed.entity

import dev.materii.gloom.core.graphql.fragment.FeedActor as FeedActorFragment

/**
 * An entity that can do an action.
 *
 * @param login Username of the actor
 * @param avatarUrl Url to the actor's avatar
 * @param isOrg Whether or not this actor is an organization
 */
data class FeedActor(
    val login: String,
    val avatarUrl: String,
    val isOrg: Boolean
) {

    companion object {

        fun fromFragment(fragment: FeedActorFragment) = with(fragment) {
            FeedActor(login = login, avatarUrl = avatarUrl, isOrg = false)
        }

    }

}