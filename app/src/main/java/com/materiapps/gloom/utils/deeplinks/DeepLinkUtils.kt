package com.materiapps.gloom.utils.deeplinks

import cafe.adriel.voyager.navigator.Navigator
import com.materiapps.gloom.domain.manager.AuthManager
import com.materiapps.gloom.ui.screens.home.HomeScreen
import com.materiapps.gloom.ui.screens.profile.ProfileScreen
import com.materiapps.gloom.utils.navigate

fun DeepLinkHandler.addAllRoutes(navigator: Navigator, auth: AuthManager) {
    addOnLinkVisitedListener("/{username}") { params, query ->
        if(!auth.isSignedIn) return@addOnLinkVisitedListener
        val username = params["username"]!!
        println(username)
        when (query["tab"]) {
            "repositories" -> println("$username's repos")
            "projects" -> println("$username's projects")
            "packages" -> println("$username's packages")
            "stars" -> println("$username's starred repos")
            else -> navigator push ProfileScreen(username)
        }
    }

    addOnLinkVisitedListener("/{owner}/{repo}") { params, _ ->
        val owner = params["owner"]!!
        val repo = params["repo"]!!
        println("Repo $repo by $owner")
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