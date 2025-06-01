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
        addOnLinkVisitedListener("/{username}") { params, query ->
            val username = params["username"]!!
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

        addOnLinkVisitedListener("/{owner}/{repo}") { params, _ ->
            val owner = params["owner"]!!
            val repo = params["repo"]!!
            listOf(RepoScreen(owner, repo))
        }

        addOnLinkVisitedListener("/{owner}/{repo}/releases/tag/{tag}") { params, _ ->
            val owner = params["owner"]!!
            val repo = params["repo"]!!
            val tag = params["tag"]!!
            listOf(ReleaseScreen(owner, repo, tag))
        }

        addOnLinkVisitedListener("/dashboard") { _, _ ->
            println("dashboard")
            emptyList()
        }

        addOnLinkVisitedListener("/orgs/{org}/dashboard") { params, _ ->
            val org = params["org"]!!
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

    private fun getQueryParams(path: String): Map<String, String> {
        val parts = path.split("?")

        if (parts.size == 1) return emptyMap()

        val parsed = mutableMapOf<String, String>()
        val queries = parts[1].split("&")

        queries.forEach {
            val query = it.split("=")

            if (query.size == 2) parsed[query[0]] = query[1]
        }

        return parsed
    }

    private fun extractParams(route: String, path: List<String>): Map<String, String> {
        val paramIndexes = mutableMapOf<Int, String>()
        val params = mutableMapOf<String, String>()
        val routePath = route.split("?").firstOrNull()?.split("/") ?: return emptyMap()

        routePath.forEachIndexed { i, segment ->
            if (segment matches paramPattern) {
                val groups = paramPattern.find(segment)!!.groups

                paramIndexes[i] = groups[1]!!.value
            }
        }

        path.forEachIndexed { i, segment ->
            val param = paramIndexes[i]

            if (param != null) params[param] = segment
        }

        return params
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
                urlParams = extractParams(path.joinToString("/"), path),
                queryParams = getQueryParams(intent.dataString!!)
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
                urlParams = extractParams(segment.joinToString("/"), path),
                queryParams = getQueryParams(intent.dataString!!)
            )
        }.orEmpty()
    }
}

fun interface OnLinkVisitedListener {
    operator fun invoke(urlParams: Map<String, String>, queryParams: Map<String, String>): List<Screen>
}