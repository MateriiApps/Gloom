package com.materiapps.gloom.domain.models

import com.materiapps.gloom.ProfileQuery
import com.materiapps.gloom.UserProfileQuery
import com.materiapps.gloom.rest.dto.user.User
import kotlinx.datetime.LocalDateTime

data class ModelUser(
    val username: String? = null,
    val displayName: String? = null,
    val bio: String? = null,
    val id: Long? = null,
    val nodeId: String? = null,
    val avatar: String? = null,
    val gravatarId: String? = null,
    val type: User.Type,
    val admin: Boolean? = null,
    val company: String? = null,
    val website: String? = null,
    val location: String? = null,
    val email: String? = null,
    val hireable: Boolean? = null,
    val twitterUsername: String? = null,
    val repos: Long? = null,
    val gists: Long? = null,
    val followers: Long? = null,
    val following: Long? = null,
    val joined: LocalDateTime? = null,
    val updated: LocalDateTime? = null,
    val status: ModelStatus? = null,
    val starred: Long? = null,
    val orgs: Long? = null
) {

    companion object {

        fun fromApi(apiUser: User) = with(apiUser) {
            ModelUser(
                username,
                displayName,
                bio,
                id,
                nodeId,
                avatar,
                gravatarId,
                type,
                admin,
                company,
                website,
                location,
                email,
                hireable,
                twitterUsername,
                repos,
                gists,
                followers,
                following,
                joined,
                updated
            )
        }

        fun fromProfileQuery(pq: ProfileQuery.Data) = with(pq.viewer) {
            ModelUser(
                username = login,
                displayName = name,
                bio = bio,
                avatar = avatarUrl.toString(),
                type = User.Type.USER,
                company = company,
                website = websiteUrl.toString(),
                twitterUsername = twitterUsername,
                repos = repositories.totalCount.toLong(),
                orgs = organizations.totalCount.toLong(),
                starred = starredRepositories.totalCount.toLong(),
                followers = followers.totalCount.toLong(),
                following = following.totalCount.toLong(),
                status = ModelStatus.fromProfileQuery(pq)
            )
        }

        fun fromUserProfileQuery(upq: UserProfileQuery.Data) = with(upq.user) {
            if(this == null)
                ModelUser(type = User.Type.USER)
            else
                ModelUser(
                    username = login,
                    displayName = name,
                    bio = bio,
                    avatar = avatarUrl.toString(),
                    type = User.Type.USER,
                    company = company,
                    website = websiteUrl.toString(),
                    twitterUsername = twitterUsername,
                    repos = repositories.totalCount.toLong(),
                    orgs = organizations.totalCount.toLong(),
                    starred = starredRepositories.totalCount.toLong(),
                    followers = followers.totalCount.toLong(),
                    following = following.totalCount.toLong(),
                    status = ModelStatus.fromUserProfileQuery(upq)
                )
        }

    }

}