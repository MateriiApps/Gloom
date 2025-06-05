package dev.materii.gloom.util.deeplink

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen

object DeepLinkHandler {

    private var uri by mutableStateOf<Uri?>(null)
    private val linkVisitedListeners = mutableMapOf<List<String>, OnLinkVisitedListener>()
    private val paramPattern = "^\\{([A-z0-9_]+)\\}$".toRegex()

    init { addAllRoutes() }

    fun addOnLinkVisitedListener(route: String, callback: OnLinkVisitedListener) {
        val r = route.removePrefix("/")

        linkVisitedListeners[r.split("/")] = callback
    }

    fun removeOnLinkVisitedListener(route: String) {
        val r = route.removePrefix("/").split("/")

        linkVisitedListeners.remove(r)
    }

    private fun extractQueryParams(path: String): Map<String, String> {
        val parts = path.split("?")

        if (parts.size <= 1) return emptyMap()

        val parsed = mutableMapOf<String, String>()
        val queries = parts[1].split("&")

        queries.forEach {
            val query = it.split("=")

            if (query.size == 2) parsed[query[0]] = query[1]
        }

        return parsed
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
    operator fun invoke(urlParams: List<String>, queryParams: Map<String, String>): List<Screen>
}