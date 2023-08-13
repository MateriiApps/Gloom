package com.materiiapps.gloom.ui.viewmodels.auth

import android.content.Context
import cafe.adriel.voyager.core.model.ScreenModel
import com.materiiapps.gloom.api.Credentials
import com.materiiapps.gloom.api.URLs
import com.materiiapps.gloom.domain.manager.Account
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.utils.openCustomTab
import io.ktor.http.URLBuilder

actual class LandingViewModel(private val context: Context, val authManager: AuthManager) : ScreenModel {

    actual fun signIn(type: Account.Type) {
        val url = URLBuilder(URLs.AUTH.LOGIN).also {
            it.parameters.apply {
                append("client_id", Credentials.CLIENT_ID)
                append("redirect_uri", "github://com.github.android/oauth")
                listOf(
                    "repo",
                    "repo:status",
                    "repo_deployment",
                    "public_repo",
                    "repo:invite",
                    "security_events",
                    "admin:repo_hook",
                    "admin:org",
                    "admin:public_key",
                    "admin:org_hook",
                    "gist",
                    "notifications",
                    "user",
                    "user:assets",
                    "project",
                    "delete_repo",
                    "write:discussion",
                    "write:packages",
                    "read:packages",
                    "delete:packages",
                    "admin:gpg_key",
                    "workflow"
                ).joinToString(" ").let { scopes ->
                    append("scope", scopes)
                }
            }
        }.buildString()

        authManager.setAuthState(authType = type)
        context.openCustomTab(url, force = true)
    }

}