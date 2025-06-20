package dev.materii.gloom.ui.screen.auth.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import dev.materii.gloom.api.Credentials
import dev.materii.gloom.api.URLs
import dev.materii.gloom.domain.manager.AuthManager
import dev.materii.gloom.ui.util.NavigationUtil
import io.ktor.http.URLBuilder

class LandingViewModel(
    val authManager: AuthManager
): ScreenModel {

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

    fun switchToAccount(id: String) {
        authManager.switchToAccount(id)
        NavigationUtil.clearRootNavigation()
    }

}