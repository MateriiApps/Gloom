package com.materiiapps.gloom.rest.dto.user

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("login")
    val username: String,
    @SerialName("name")
    val displayName: String? = null,
    val bio: String? = null,
    val id: Long,
    @SerialName("node_id")
    val nodeId: String,
    @SerialName("avatar_url")
    val avatar: String,
    @SerialName("gravatar_id")
    val gravatarId: String,
    val type: Type,
    @SerialName("site_admin")
    val admin: Boolean,
    val company: String? = null,
    @SerialName("blog")
    val website: String? = null,
    val location: String? = null,
    val email: String? = null,
    val hireable: Boolean? = null,
    @SerialName("twitter_username")
    val twitterUsername: String? = null,
    @SerialName("public_repos")
    val repos: Long,
    @SerialName("public_gists")
    val gists: Long,
    val followers: Long,
    val following: Long,
    @SerialName("created_at")
    val joined: LocalDateTime,
    @SerialName("updated_at")
    val updated: LocalDateTime
) {

    @Serializable
    enum class Type {
        @SerialName("User")
        USER,
        @SerialName("Organization")
        ORG,
    }

}