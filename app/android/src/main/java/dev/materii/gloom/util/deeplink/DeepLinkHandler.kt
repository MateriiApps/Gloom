package dev.materii.gloom.util.deeplink

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DeepLinkHandler {
    private var uri by mutableStateOf<Uri?>(null)
    private var linkVisitedListeners = mutableMapOf<List<String>, OnLinkVisitedListener>()

    fun addOnLinkVisitedListener(route: String, callback: OnLinkVisitedListener) {
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

    fun handle(intent: Intent) {
        val path = intent.data?.pathSegments ?: return
        val routes = linkVisitedListeners.keys.filter {
            it.size == path.size
        }

        if (routes.isEmpty()) return

        linkVisitedListeners[path]?.let { listener ->
            listener(
                urlParams = extractParams(path.joinToString("/"), path),
                queryParams = getQueryParams(intent.dataString!!)
            )

            uri = intent.data

            return
        }

        routes.firstOrNull { route ->
            var matched = false

            path.forEachIndexed { index, pSeg ->
                val rSeg = route[index]

                matched = rSeg == pSeg || rSeg matches paramPattern
            }

            matched
        }?.let { segment ->
            linkVisitedListeners[segment]?.invoke(
                urlParams = extractParams(segment.joinToString("/"), path),
                queryParams = getQueryParams(intent.dataString!!)
            )

            uri = intent.data
        }

    }

    private companion object {
        val paramPattern = "^\\{([A-z0-9_]+)\\}\$".toRegex()
    }
}

fun interface OnLinkVisitedListener {
    operator fun invoke(urlParams: Map<String, String>, queryParams: Map<String, String>)
}