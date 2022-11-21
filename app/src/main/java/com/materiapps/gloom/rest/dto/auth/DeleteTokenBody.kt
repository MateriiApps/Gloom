package com.materiapps.gloom.rest.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteTokenBody(
    @SerialName("access_token")
    val accessToken: String
)