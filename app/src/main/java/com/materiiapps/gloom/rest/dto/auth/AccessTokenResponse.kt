package com.materiiapps.gloom.rest.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    @SerialName("access_token")
    val accessToken: String,
    val scope: String,
    @SerialName("token_type")
    val tokenType: String
)