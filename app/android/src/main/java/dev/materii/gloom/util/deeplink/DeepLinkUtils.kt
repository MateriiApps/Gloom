package dev.materii.gloom.util.deeplink

import cafe.adriel.voyager.navigator.Navigator
import dev.materii.gloom.domain.manager.AuthManager
import dev.materii.gloom.ui.screen.list.RepositoryListScreen
import dev.materii.gloom.ui.screen.list.StarredReposListScreen
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import dev.materii.gloom.ui.screen.release.ReleaseScreen
import dev.materii.gloom.ui.screen.repo.RepoScreen

fun DeepLinkHandler.addAllRoutes(navigator: Navigator, auth: AuthManager) {
    addOnLinkVisitedListener("/{username}") { params, query ->
        if (!auth.isSignedIn) return@addOnLinkVisitedListener
        val username = params["username"]!!
        when (query["tab"]) {
            "repositories" -> navigator push listOf(
                ProfileScreen(username),
                RepositoryListScreen(username)
            )

            "projects" -> println("$username's projects")
            "packages" -> println("$username's packages")
            "stars" -> navigator push listOf(
                ProfileScreen(username),
                StarredReposListScreen(username)
            )

            else -> navigator push ProfileScreen(username)
        }
    }

    addOnLinkVisitedListener("/{owner}/{repo}") { params, _ ->
        val owner = params["owner"]!!
        val repo = params["repo"]!!
        navigator push RepoScreen(owner, repo)
    }

    addOnLinkVisitedListener("/{owner}/{repo}/releases/tag/{tag}") { params, _ ->
        val owner = params["owner"]!!
        val repo = params["repo"]!!
        val tag = params["tag"]!!
        navigator push ReleaseScreen(owner, repo, tag)
    }

    addOnLinkVisitedListener("/dashboard") { _, _ ->
        println("dashboard")
    }

    addOnLinkVisitedListener("/orgs/{org}/dashboard") { params, _ ->
        val org = params["org"]!!
        println("$org's dashboard")
    }

    addOnLinkVisitedListener("/settings/organizations") { _, _ ->
        println("users orgs")
    }
}