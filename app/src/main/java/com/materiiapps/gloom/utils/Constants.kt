@file:Suppress("FunctionName")

package com.materiiapps.gloom.utils

import android.util.Base64
import com.materiiapps.gloom.BuildConfig

object Constants {

    const val DEV_USER_ID = "MDQ6VXNlcjQ0OTkyNTM3"

}

object Credentials {

    val CLIENT_ID = String(Base64.decode(BuildConfig.CLIENT_ID, Base64.DEFAULT))

    val CLIENT_SECRET = String(Base64.decode(BuildConfig.CLIENT_SECRET, Base64.DEFAULT))

    val BASIC_TOKEN: String =
        Base64.encodeToString("$CLIENT_ID:$CLIENT_SECRET".toByteArray(), Base64.NO_WRAP)

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