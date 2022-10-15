package com.materiapps.gloom.domain.manager

import android.content.Context
import com.materiapps.gloom.domain.manager.base.BasePreferenceManager
import com.materiapps.gloom.domain.repository.GithubAuthRepository
import com.materiapps.gloom.rest.utils.ifSuccessful
import com.materiapps.gloom.utils.coroutine

class AuthManager(context: Context, private val authRepo: GithubAuthRepository) :
    BasePreferenceManager(context.getSharedPreferences("auth", Context.MODE_PRIVATE)) {

    var authToken by stringPreference("authToken", "")
    var refreshToken by stringPreference("refreshToken", "")

    val isSignedIn = authToken.isNotEmpty()

    fun refreshAccessToken() {
        coroutine {
            if (refreshToken.isNotEmpty()) authRepo.refreshAccessToken(refreshToken).also {
                it.ifSuccessful { token ->
                    authToken = token.accessToken
                    refreshToken = token.refreshToken
                }
            }
        }
    }
}