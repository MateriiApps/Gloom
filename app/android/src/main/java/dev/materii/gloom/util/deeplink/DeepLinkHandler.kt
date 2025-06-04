package dev.materii.gloom.util.deeplink

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import dev.materii.gloom.ui.screen.list.RepositoryListScreen
import dev.materii.gloom.ui.screen.list.StarredReposListScreen
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import dev.materii.gloom.ui.screen.release.ReleaseScreen
import dev.materii.gloom.ui.screen.repo.RepoScreen

object DeepLinkHandler {
    private var uri by mutableStateOf<Uri?>(null)
    private val linkVisitedListeners = mutableMapOf<List<String>, OnLinkVisitedListener>()
    private val paramPattern = "^\\{([A-z0-9_]+)\\}$".toRegex()

    init {
        addOnLinkVisitedListener("/{username}") { (username), (tab) ->
            when (tab) {
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

        addOnLinkVisitedListener("/dashboard") { _, _ ->
            println("dashboard")
            emptyList()
        }

        addOnLinkVisitedListener("/orgs/{org}/dashboard") { (org), _ ->
            emptyList()
        }

        addOnLinkVisitedListener("/settings/organizations") { _, _ ->
            emptyList()
        }
    }

    private fun addOnLinkVisitedListener(route: String, callback: OnLinkVisitedListener) {
        val r = route.removePrefix("/")

        linkVisitedListeners[r.split("/")] = callback
    }

    fun removeOnLinkVisitedListener(route: String) {
        val r = route.removePrefix("/").split("/")

        linkVisitedListeners.remove(r)
    }

    private fun extractQueryParams(path: String): List<String> {
        val parts = path.split("?")

        if (parts.size <= 1) return emptyList()

        return parts[1].split("&").mapNotNull { param ->
            val query = param.substringAfter("=", "")
            query.ifBlank { null }
        }
    }

    private fun extractUrlParams(route: String, path: List<String>): List<String> {
        val routePath = route.split("?").firstOrNull()?.split("/").orEmpty()

        return routePath.indices
            .filter { i -> i < path.size && routePath[i] matches paramPattern }
            .map { i -> path[i] }
    }

    /**
     * Handles the incoming intent and returns a list of screens based on the deep link.
     *
     * @param intent The incoming intent containing the deep link data.
     * @return A list of screens to be displayed based on the deep link.
     */
    fun handle(intent: Intent): List<Screen> {
        val path = intent.data?.pathSegments ?: return emptyList()
        val routes = linkVisitedListeners.keys.filter { it.size == path.size }

        if (routes.isEmpty()) return emptyList()

        linkVisitedListeners[path]?.let { listener ->
            uri = intent.data

            return listener(
                urlParams = extractUrlParams(path.joinToString("/"), path),
                queryParams = extractQueryParams(intent.dataString!!)
            )
        }

        return routes.firstOrNull { route ->
            var matched = false

            path.forEachIndexed { index, pSeg ->
                val rSeg = route[index]

                matched = rSeg == pSeg || rSeg matches paramPattern
            }

            matched
        }?.let { segment ->
            uri = intent.data

            linkVisitedListeners[segment]?.invoke(
                urlParams = extractUrlParams(segment.joinToString("/"), path),
                queryParams = extractQueryParams(intent.dataString!!)
            )
        }.orEmpty()
    }
}

fun interface OnLinkVisitedListener {
    /**
     * @param urlParams List of URL parameters extracted from the deep link in the order they appear in the route.
     */
    operator fun invoke(urlParams: List<String>, queryParams: List<String>): List<Screen>
}