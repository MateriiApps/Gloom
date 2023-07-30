package com.materiiapps.gloom.api

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
object Credentials {

    val CLIENT_ID: String = String(Base64.decode(BuildConfig.CLIENT_ID))

    val CLIENT_SECRET: String = String(Base64.decode(BuildConfig.CLIENT_SECRET))

    val BASIC_TOKEN: String =
        Base64.encode("$CLIENT_ID:$CLIENT_SECRET".toByteArray())

}

object URLs {

    const val BASE_URL = "https://api.github.com"

    const val GRAPHQL = "${BASE_URL}/graphql"

    object AUTH {
        const val ACCESS_TOKEN = "https://github.com/login/oauth/access_token"
        const val LOGIN = "https://github.com/login/oauth/authorize"
        fun DELETE_TOKEN(clientId: String) = "$BASE_URL/applications/$clientId/token"
    }

}