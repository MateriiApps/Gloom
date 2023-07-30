package com.materiiapps.gloom.ui.viewmodels.repo.tab

import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.gql.RepoReleasesQuery
import com.materiiapps.gloom.gql.fragment.ReleaseItem
import com.materiiapps.gloom.api.utils.getOrNull
import com.materiiapps.gloom.ui.viewmodels.list.base.BaseListViewModel

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