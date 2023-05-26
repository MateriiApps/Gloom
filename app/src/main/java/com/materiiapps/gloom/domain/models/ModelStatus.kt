package com.materiiapps.gloom.domain.models

import com.materiiapps.gloom.gql.ProfileQuery
import com.materiiapps.gloom.gql.UserProfileQuery

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