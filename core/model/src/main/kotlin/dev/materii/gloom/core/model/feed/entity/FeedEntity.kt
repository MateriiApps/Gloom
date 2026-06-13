package dev.materii.gloom.core.model.feed.entity

interface FeedEntity {

    val id: String
    val login: String
    val displayName: String?
    val avatarUrl: String
    val bio: String?
    val isCurrentUserFollowing: Boolean
    val counts: Counts
}