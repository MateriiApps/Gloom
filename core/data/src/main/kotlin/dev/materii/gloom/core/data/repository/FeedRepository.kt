package dev.materii.gloom.core.data.repository

import dev.materii.gloom.core.common.api.graphql.GraphQLResponseFlow
import dev.materii.gloom.core.common.api.graphql.transform
import dev.materii.gloom.core.graphql.GraphQLDataSource
import dev.materii.gloom.core.model.feed.item.FeedItem
import dev.materii.gloom.core.model.paging.Page

interface FeedRepository {

    /**
     * Get the current user's activity feed
     *
     * @param after The cursor used to retrieve the next page of items
     */
    fun getFeed(after: String? = null): GraphQLResponseFlow<Page<FeedItem>>

}

internal class FeedRepositoryImpl(
    private val graphQL: GraphQLDataSource
): FeedRepository {

    override fun getFeed(after: String?): GraphQLResponseFlow<Page<FeedItem>> {
        return graphQL.getFeed(after).transform { (viewer) ->
            if (viewer.dashboard == null) return@transform Page(items = emptyList())
            val items = viewer.dashboard!!.feed.items

            Page(
                next = items.pageInfo.endCursor,
                totalCount = items.totalCount,
                items = items.nodes.orEmpty().filterNotNull().map { FeedItem.fromFragment(it) }
            )
        }
    }

}