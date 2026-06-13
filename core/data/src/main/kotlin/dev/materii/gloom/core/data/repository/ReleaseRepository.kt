package dev.materii.gloom.core.data.repository

import dev.materii.gloom.core.common.api.graphql.GraphQLResponseFlow
import dev.materii.gloom.core.common.api.graphql.transform
import dev.materii.gloom.core.graphql.GraphQLDataSource
import dev.materii.gloom.core.graphql.fragment.ReleaseDetails

interface ReleaseRepository {

    /**
     * Get the details and assets for a release
     *
     * @param owner Owner of the repository the release is from
     * @param name Name of the repository the release is from
     * @param tag Tag associated with the release
     * @param after Cursor used to get the next set of assets
     */
    fun getReleaseDetails(
        owner: String,
        name: String,
        tag: String,
        after: String? = null
    ): GraphQLResponseFlow<ReleaseDetails?>

}

internal class ReleaseRepositoryImpl(
    private val graphQL: GraphQLDataSource
): ReleaseRepository {

    override fun getReleaseDetails(
        owner: String,
        name: String,
        tag: String,
        after: String?
    ): GraphQLResponseFlow<ReleaseDetails?> {
        return graphQL.getReleaseDetails(owner, name, tag, after).transform {
            it.repository?.release?.releaseDetails
        }
    }

}