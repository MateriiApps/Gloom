package com.materiapps.gloom.domain.models

import com.materiapps.gloom.ProfileQuery
import com.materiapps.gloom.UserProfileQuery

data class ModelStatus(
    val emoji: String?,
    val message: String?
) {

    companion object {

        fun fromProfileQuery(pq: ProfileQuery.Data) = with(pq.viewer.userProfile.status) {
            if (this == null)
                ModelStatus(null, null)
            else
                ModelStatus(emoji, message)
        }

        fun fromUserProfileQuery(upq: UserProfileQuery.Data) =
            with(upq.repositoryOwner?.userProfile?.status) {
                if (this == null)
                    ModelStatus(null, null)
                else
                    ModelStatus(emoji, message)
            }

    }

}