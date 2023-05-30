package com.materiiapps.gloom.utils.deeplinks

import cafe.adriel.voyager.navigator.Navigator
import com.materiiapps.gloom.ui.screens.repo.RepoScreen
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.ui.screens.list.RepositoryListScreen
import com.materiiapps.gloom.ui.screens.list.StarredReposListScreen
import com.materiiapps.gloom.ui.screens.profile.ProfileScreen

fun DeepLinkHandler.addAllRoutes(navigator: Navigator, auth: AuthManager) {
    addOnLinkVisitedListener("/{username}") { params, query ->
        if(!auth.isSignedIn) return@addOnLinkVisitedListener
        val username = params["username"]!!
        when (query["tab"]) {
            "repositories" -> navigator push listOf(ProfileScreen(username), RepositoryListScreen(username))
            "projects" -> println("$username's projects")
            "packages" -> println("$username's packages")
            "stars" -> navigator push listOf(ProfileScreen(username), StarredReposListScreen(username))
            else -> navigator push ProfileScreen(username)
        }
    }

    addOnLinkVisitedListener("/{owner}/{repo}") { params, _ ->
        val owner = params["owner"]!!
        val repo = params["repo"]!!
        navigator push RepoScreen(owner, repo)
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