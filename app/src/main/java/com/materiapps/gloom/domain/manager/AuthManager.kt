package com.materiapps.gloom.domain.manager

import android.content.Context
import com.materiapps.gloom.domain.manager.base.BasePreferenceManager

class AuthManager(context: Context) :
    BasePreferenceManager(context.getSharedPreferences("auth", Context.MODE_PRIVATE)) {
    var authToken by stringPreference("authToken", "")

    val isSignedIn = authToken.isNotEmpty()
}