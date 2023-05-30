package com.materiiapps.gloom.domain.manager

import android.content.Context
import androidx.core.content.edit
import com.materiiapps.gloom.domain.repository.GithubAuthRepository

class AuthManager(context: Context, private val authRepo: GithubAuthRepository) {

    private val sharedPrefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    var authToken: String
        get() = sharedPrefs.getString("authToken", "")!!
        set(value) = sharedPrefs.edit {
            putString("authToken", value)
        }

    val isSignedIn get() = authToken.isNotEmpty()

}