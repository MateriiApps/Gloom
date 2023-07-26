package com.materiiapps.gloom.domain.models

import com.materiiapps.gloom.gql.FollowersQuery
import com.materiiapps.gloom.gql.FollowingQuery
import com.materiiapps.gloom.gql.JoinedOrgsQuery
import com.materiiapps.gloom.gql.ProfileQuery
import com.materiiapps.gloom.gql.UserProfileQuery
import com.materiiapps.gloom.gql.fragment.Contributions
import com.materiiapps.gloom.gql.fragment.OrgSponsoringFragment
import com.materiiapps.gloom.gql.fragment.Social
import com.materiiapps.gloom.gql.fragment.UserSponsoringFragment
import com.materiiapps.gloom.gql.type.SocialAccountProvider
import com.materiiapps.gloom.rest.dto.user.User
import kotlinx.datetime.LocalDateTime

data class ModelUser(
    val username: String? = null,
    val displayName: String? = null,
    val bio: String? = null,
    val id: String? = null,
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
    val socials: List<Social> = emptyList(),
    val repos: Long? = null,
    val gists: Long? = null,
    val followers: Long? = null,
    val following: Long? = null,
    val sponsoring: Long? = null,
    val joined: LocalDateTime? = null,
    val updated: LocalDateTime? = null,
    val status: ModelStatus? = null,
    val starred: Long? = null,
    val orgs: Long? = null,
    val isMember: Boolean? = null,
    val pinnedItems: List<Pinnable?> = emptyList(),
    val canFollow: Boolean? = null,
    val isFollowing: Boolean? = null,
    val isSupporter: Boolean = false,
    val isFollowingYou: Boolean = false,
    val contributions: Contributions? = null
) {

    companion object {

        fun fromApi(apiUser: User) = with(apiUser) {
            ModelUser(
                username = username,
                displayName = displayName,
                bio = bio,
                id = id.toString(),
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
                socials = listOf(
                    Social(
                        "@$twitterUsername",
                        SocialAccountProvider.TWITTER,
                        "https://twitter.com/$twitterUsername"
                    )
                ),
                repos = repos,
                gists = gists,
                followers = followers,
                following = following,
                joined = joined,
                updated = updated
            )
        }

        fun fromProfileQuery(pq: ProfileQuery.Data) = with(pq) {
            with(viewer.userProfile) {
                ModelUser(
                    id = id,
                    username = login,
                    displayName = name,
                    bio = bio,
                    avatar = avatarUrl.toString(),
                    readme = profileReadme?.contentHTML?.toString(),
                    type = User.Type.USER,
                    company = company,
                    website = websiteUrl.toString(),
                    socials = socialAccounts.nodes?.mapNotNull { it?.social } ?: emptyList(),
                    repos = repositories.totalCount.toLong(),
                    orgs = organizations.totalCount.toLong(),
                    starred = starredRepositories.totalCount.toLong(),
                    sponsoring = sponsoring.totalCount.toLong(),
                    followers = followers.totalCount.toLong(),
                    following = following.totalCount.toLong(),
                    status = ModelStatus.fromProfileQuery(pq),
                    email = email,
                    location = location,
                    pinnedItems = pinnedItems.nodes?.map { ModelRepo.fromPinnedRepo(it?.pinnedRepo) }
                        ?: emptyList(),
                    canFollow = viewerCanFollow,
                    isFollowing = viewerIsFollowing,
                    isSupporter = user?.viewerIsSponsoring ?: false,
                    isFollowingYou = isFollowingViewer,
                    contributions = contributionsCollection.contributions
                )
            }
        }

        fun fromUserProfileQuery(upq: UserProfileQuery.Data) = with(upq) {
            val isUser = upq.repositoryOwner?.userProfile != null
            val isOrg = upq.repositoryOwner?.orgProfile != null && !isUser
            val isSupporter = user?.isSponsoredBy ?: false

            if (isUser) {
                with(upq.repositoryOwner!!.userProfile!!) {
                    ModelUser(
                        id = id,
                        username = login,
                        displayName = name,
                        bio = bio,
                        avatar = avatarUrl.toString(),
                        readme = profileReadme?.contentHTML?.toString(),
                        type = User.Type.USER,
                        company = company,
                        website = websiteUrl.toString(),
                        socials = socialAccounts.nodes?.mapNotNull { it?.social } ?: emptyList(),
                        repos = repositories.totalCount.toLong(),
                        orgs = organizations.totalCount.toLong(),
                        starred = starredRepositories.totalCount.toLong(),
                        sponsoring = sponsoring.totalCount.toLong(),
                        followers = followers.totalCount.toLong(),
                        following = following.totalCount.toLong(),
                        status = ModelStatus.fromUserProfileQuery(upq),
                        email = email,
                        location = location,
                        pinnedItems = pinnedItems.nodes?.map { ModelRepo.fromPinnedRepo(it?.pinnedRepo) }
                            ?: emptyList(),
                        canFollow = viewerCanFollow,
                        isFollowing = viewerIsFollowing,
                        isSupporter = isSupporter,
                        isFollowingYou = isFollowingViewer,
                        contributions = contributionsCollection.contributions
                    )
                }
            } else if (isOrg) {
                with(upq.repositoryOwner!!.orgProfile!!) {
                    ModelUser(
                        username = login,
                        displayName = name,
                        bio = bio,
                        avatar = avatarUrl.toString(),
                        readme = readme?.contentHTML?.toString(),
                        type = User.Type.ORG,
                        website = websiteUrl.toString(),
                        socials = twitterUsername?.let {
                            listOf(
                                Social(
                                    "@$it",
                                    SocialAccountProvider.TWITTER,
                                    "https://twitter.com/$it"
                                )
                            )
                        } ?: emptyList(),
                        email = publicEmail,
                        location = location,
                        repos = repositories.totalCount.toLong(),
                        sponsoring = sponsoring.totalCount.toLong(),
                        isMember = viewerIsAMember,
                        pinnedItems = pinnedItems.nodes?.map { ModelRepo.fromPinnedRepo(it?.pinnedRepo) }
                            ?: emptyList(),
                        isSupporter = isSupporter
                    )
                }
            } else {
                ModelUser(type = User.Type.USER)
            }

        }

        fun fromJoinedOrgsQuery(joq: JoinedOrgsQuery.Node) = with(joq.orgListOrgFragment) {
            ModelUser(
                username = login,
                displayName = name,
                bio = description,
                avatar = avatarUrl,
                type = User.Type.ORG
            )
        }

        fun fromFollowersQuery(followersQuery: FollowersQuery.Node) = with(followersQuery.userListUserFragment) {
            ModelUser(
                username = login,
                displayName = name,
                bio = bio,
                avatar = avatarUrl,
                type = User.Type.USER
            )
        }

        fun fromFollowingQuery(followingQuery: FollowingQuery.Node) = with(followingQuery.userListUserFragment) {
            ModelUser(
                username = login,
                displayName = name,
                bio = bio,
                avatar = avatarUrl,
                type = User.Type.USER
            )
        }

        fun fromSponsoringQuery(sponsoringQuery: UserSponsoringFragment.Node) = with(sponsoringQuery) {
            val isUser = sponsoringQuery.userListUserFragment != null
            val isOrg = sponsoringQuery.orgListOrgFragment != null && !isUser

            if (isUser) {
                with(sponsoringQuery.userListUserFragment!!) {
                    ModelUser(
                        username = login,
                        displayName = name,
                        bio = bio,
                        avatar = avatarUrl,
                        type = User.Type.USER
                    )
                }
            } else if (isOrg) {
                with(sponsoringQuery.orgListOrgFragment!!) {
                    ModelUser(
                        username = login,
                        displayName = name,
                        bio = description,
                        avatar = avatarUrl,
                        type = User.Type.USER
                    )
                }
            } else {
                ModelUser(type = User.Type.USER)
            }
        }

        fun fromSponsoringQuery(sponsoringQuery: OrgSponsoringFragment.Node) = with(sponsoringQuery) {
            val isUser = sponsoringQuery.userListUserFragment != null
            val isOrg = sponsoringQuery.orgListOrgFragment != null && !isUser

            if (isUser) {
                with(sponsoringQuery.userListUserFragment!!) {
                    ModelUser(
                        username = login,
                        displayName = name,
                        bio = bio,
                        avatar = avatarUrl,
                        type = com.materiiapps.gloom.rest.dto.user.User.Type.USER
                    )
                }
            } else if (isOrg) {
                with(sponsoringQuery.orgListOrgFragment!!) {
                    ModelUser(
                        username = login,
                        displayName = name,
                        bio = description,
                        avatar = avatarUrl,
                        type = com.materiiapps.gloom.rest.dto.user.User.Type.USER
                    )
                }
            } else {
                ModelUser(type = com.materiiapps.gloom.rest.dto.user.User.Type.USER)
            }
        }

    }

}