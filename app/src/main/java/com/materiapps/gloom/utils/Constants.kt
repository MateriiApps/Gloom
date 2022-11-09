package com.materiapps.gloom.utils

import android.util.Base64
import com.materiapps.gloom.BuildConfig

object Credentials {

    val CLIENT_ID = String(Base64.decode(BuildConfig.CLIENT_ID, Base64.DEFAULT))

    val CLIENT_SECRET = String(Base64.decode(BuildConfig.CLIENT_SECRET, Base64.DEFAULT))

}

object URLs {

    const val BASE_URL = "https://api.github.com"

    const val GRAPHQL = "${BASE_URL}/graphql"

    object AUTH {
        const val ACCESS_TOKEN = "https://github.com/login/oauth/access_token"
        const val LOGIN = "https://github.com/login/oauth/authorize"
    }

    object REPOS {

        fun README(owner: String, repo: String) = "$BASE_URL/repos/$owner/$repo/readme"

    }

    object ORG {

        fun README(org: String, isMember: Boolean) = "$BASE_URL/repos/$org/.github${if (isMember) "-private" else ""}/readme/profile"

    }

}