package com.materiiapps.gloom.ui.viewmodels.auth

import android.content.Context
import cafe.adriel.voyager.core.model.ScreenModel
import com.materiiapps.gloom.api.Credentials
import com.materiiapps.gloom.utils.URLs
import com.materiiapps.gloom.utils.openCustomTab
import io.ktor.http.URLBuilder

class LandingViewModel : ScreenModel {

    fun signIn(context: Context) {
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

        context.openCustomTab(url, force = true)
    }

}