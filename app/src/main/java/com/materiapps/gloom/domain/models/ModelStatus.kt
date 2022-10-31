package com.materiapps.gloom.domain.models

import com.materiapps.gloom.ProfileQuery
import com.materiapps.gloom.UserProfileQuery

data class ModelStatus(
    val emoji: String?,
    val message: String?
) {

    companion object {

        fun fromProfileQuery(pq: ProfileQuery.Data) = with(pq.viewer.status) {
            if(this == null)
                ModelStatus(null, null)
            else
                ModelStatus(emoji, message)
        }

        fun fromUserProfileQuery(upq: UserProfileQuery.Data) = with(upq.user?.status) {
            if(this == null)
                ModelStatus(null, null)
            else
                ModelStatus(emoji, message)
        }

    }

}