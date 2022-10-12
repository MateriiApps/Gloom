package com.materiapps.gloom.rest.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//{
//    "access_token": "ghu_16C7e42F292c6912E7710c838347Ae178B4a",
//    "expires_in": 28800,
//    "refresh_token": "ghr_1B4a2e77838347a7E420ce178F2E7c6912E169246c34E1ccbF66C46812d16D5B1A9Dc86A1498",
//    "refresh_token_expires_in": 15811200,
//    "scope": "",
//    "token_type": "bearer"
//}

@Serializable
data class AccessTokenResponse(

    @SerialName("access_token")
    val accessToken: String? = null,

    @SerialName("expires_in")
    val expiresIn: Long? = null,

    @SerialName("refresh_token")
    val refreshToken: String? = null,

    @SerialName("refresh_token_expires_in")
    val refreshTokenExpiresIn: Long? = null,

    val scope: String? = null,

    @SerialName("token_type")
    val tokenType: String? = null

)