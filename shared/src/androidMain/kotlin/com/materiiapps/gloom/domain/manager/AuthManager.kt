package com.materiiapps.gloom.domain.manager

import android.content.Context
import androidx.core.content.edit
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.apolloStore

actual class AuthManager(context: Context, private val apollo: ApolloClient) {

    private val sharedPrefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    actual var authToken: String
        get() = sharedPrefs.getString("authToken", "")!!
        set(value) = sharedPrefs.edit {
            putString("authToken", value)
        }

    actual val isSignedIn: Boolean get() = authToken.isNotEmpty()

    actual fun clearApolloCache() {
        apollo.apolloStore.clearAll()
    }

}