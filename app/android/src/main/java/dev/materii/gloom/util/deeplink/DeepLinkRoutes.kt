package dev.materii.gloom.util.deeplink

import dev.materii.gloom.ui.screen.list.RepositoryListScreen
import dev.materii.gloom.ui.screen.list.StarredReposListScreen
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import dev.materii.gloom.ui.screen.release.ReleaseScreen
import dev.materii.gloom.ui.screen.repo.RepoScreen

fun addAllRoutes() {
    with(DeepLinkHandler) {
        addOnLinkVisitedListener("/{username}") { (username), query ->
            when (query["tab"]) {
                "repositories" -> listOf(
                    ProfileScreen(username),
                    RepositoryListScreen(username)
                )

                "projects" -> emptyList()
                "packages" -> emptyList()
                "stars" -> listOf(ProfileScreen(username), StarredReposListScreen(username))

                else -> listOf(ProfileScreen(username))
            }
        }

        addOnLinkVisitedListener("/{owner}/{repo}") { (owner, repo), _ ->
            listOf(RepoScreen(owner, repo))
        }

        addOnLinkVisitedListener("/{owner}/{repo}/releases/tag/{tag}") { (owner, repo, tag), _ ->
            listOf(ReleaseScreen(owner, repo, tag))
        }
    }
}