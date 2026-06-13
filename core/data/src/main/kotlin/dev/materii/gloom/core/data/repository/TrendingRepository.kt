package dev.materii.gloom.core.data.repository

import dev.materii.gloom.core.common.api.graphql.GraphQLResponseFlow
import dev.materii.gloom.core.common.api.graphql.transform
import dev.materii.gloom.core.graphql.GraphQLDataSource
import dev.materii.gloom.core.graphql.type.TrendingPeriod
import dev.materii.gloom.core.graphql.fragment.TrendingRepository as TrendingRepositoryFragment

interface TrendingRepository {

    /**
     * Get the trending repositories for a given time period
     *
     * @param period The time period to compare in
     */
    fun getTrending(period: TrendingPeriod = TrendingPeriod.DAILY): GraphQLResponseFlow<List<TrendingRepositoryFragment>>

}

internal class TrendingRepositoryImpl(
    private val graphQL: GraphQLDataSource
): TrendingRepository {

    override fun getTrending(period: TrendingPeriod): GraphQLResponseFlow<List<TrendingRepositoryFragment>> {
        return graphQL.getTrending(period).transform { (trendingRepositories) ->
            trendingRepositories?.mapNotNull { it?.trendingRepository }.orEmpty()
        }
    }

}