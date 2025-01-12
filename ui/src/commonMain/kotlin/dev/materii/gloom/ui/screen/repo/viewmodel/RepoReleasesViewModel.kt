package dev.materii.gloom.ui.screen.repo.viewmodel

import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.getOrNull
import dev.materii.gloom.gql.RepoReleasesQuery
import dev.materii.gloom.gql.fragment.ReleaseItem
import dev.materii.gloom.ui.screen.list.viewmodel.BaseListViewModel

class RepoReleasesViewModel(
    private val gql: GraphQLRepository,
    nameWithOwner: Pair<String, String>
) : BaseListViewModel<ReleaseItem, RepoReleasesQuery.Data?>() {

    val owner = nameWithOwner.first
    val name = nameWithOwner.second

    override suspend fun loadPage(cursor: String?): RepoReleasesQuery.Data? =
        gql.getRepoReleases(owner, name, cursor).getOrNull()

    override fun getCursor(data: RepoReleasesQuery.Data?): String? =
        data?.repository?.releases?.pageInfo?.endCursor

    override fun createItems(data: RepoReleasesQuery.Data?): List<ReleaseItem> =
        data?.repository?.releases?.nodes?.mapNotNull { it?.releaseItem } ?: emptyList()
}