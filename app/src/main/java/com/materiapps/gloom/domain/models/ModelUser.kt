package com.materiapps.gloom.domain.models

import com.materiapps.gloom.FollowersQuery
import com.materiapps.gloom.FollowingQuery
import com.materiapps.gloom.JoinedOrgsQuery
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
    val readme: String? = null,
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
    val orgs: Long? = null,
    val isMember: Boolean? = null
) {

    companion object {

        fun fromApi(apiUser: User) = with(apiUser) {
            ModelUser(
                username = username,
                displayName = displayName,
                bio = bio,
                id = id,
                nodeId = nodeId,
                avatar = avatar,
                gravatarId = gravatarId,
                type = type,
                admin = admin,
                company = company,
                website = website,
                location = location,
                email = email,
                hireable = hireable,
                twitterUsername = twitterUsername,
                repos = repos,
                gists = gists,
                followers = followers,
                following = following,
                joined = joined,
                updated = updated
            )
        }

        fun fromProfileQuery(pq: ProfileQuery.Data) = with(pq.viewer) {
            ModelUser(
                username = login,
                displayName = name,
                bio = bio,
                avatar = avatarUrl.toString(),
                readme = profileReadme?.contentHTML?.toString(),
                type = User.Type.USER,
                company = company,
                website = websiteUrl.toString(),
                twitterUsername = twitterUsername,
                repos = repositories.totalCount.toLong(),
                orgs = organizations.totalCount.toLong(),
                starred = starredRepositories.totalCount.toLong(),
                followers = followers.totalCount.toLong(),
                following = following.totalCount.toLong(),
                status = ModelStatus.fromProfileQuery(pq),
                email = email,
                location = location
            )
        }

        fun fromUserProfileQuery(upq: UserProfileQuery.Data) = with(upq) {
            val isUser = upq.repositoryOwner?.onUser != null
            val isOrg = upq.repositoryOwner?.onOrganization != null && !isUser

            if(isUser) {
                with(upq.repositoryOwner!!.onUser!!) {
                    ModelUser(
                        username = login,
                        displayName = name,
                        bio = bio,
                        avatar = avatarUrl.toString(),
                        readme = profileReadme?.contentHTML?.toString(),
                        type = User.Type.USER,
                        company = company,
                        website = websiteUrl.toString(),
                        twitterUsername = twitterUsername,
                        repos = repositories.totalCount.toLong(),
                        orgs = organizations.totalCount.toLong(),
                        starred = starredRepositories.totalCount.toLong(),
                        followers = followers.totalCount.toLong(),
                        following = following.totalCount.toLong(),
                        status = ModelStatus.fromUserProfileQuery(upq),
                        email = publicEmail,
                        location = location
                    )
                }
            } else if (isOrg) {
                with(upq.repositoryOwner!!.onOrganization!!) {
                    ModelUser(
                        username = login,
                        displayName = name,
                        bio = bio,
                        avatar = avatarUrl.toString(),
                        readme = readme?.contentHTML?.toString(),
                        type = User.Type.ORG,
                        website = websiteUrl.toString(),
                        twitterUsername = twitterUsername,
                        email = email,
                        location = location,
                        repos = repositories.totalCount.toLong(),
                        isMember = viewerIsAMember
                    )
                }
            } else {
                ModelUser(type = User.Type.USER)
            }

        }

        fun fromJoinedOrgsQuery(joq: JoinedOrgsQuery.Node) = with(joq) {
            ModelUser(
                username = login,
                displayName = name,
                bio = description,
                avatar = avatarUrl.toString(),
                type = User.Type.ORG
            )
        }

        fun fromFollowersQuery(followersQuery: FollowersQuery.Node) = with(followersQuery) {
            ModelUser(
                username = login,
                displayName = name,
                bio = bio,
                avatar = avatarUrl.toString(),
                type = User.Type.USER
            )
        }

        fun fromFollowingQuery(followingQuery: FollowingQuery.Node) = with(followingQuery) {
            ModelUser(
                username = login,
                displayName = name,
                bio = bio,
                avatar = avatarUrl.toString(),
                type = User.Type.USER
            )
        }

    }

}