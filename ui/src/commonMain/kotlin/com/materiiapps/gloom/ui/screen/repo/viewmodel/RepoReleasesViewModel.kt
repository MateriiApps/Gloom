package com.materiiapps.gloom.ui.screen.repo.viewmodel

import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.util.getOrNull
import com.materiiapps.gloom.gql.RepoReleasesQuery
import com.materiiapps.gloom.gql.fragment.ReleaseItem
import com.materiiapps.gloom.ui.screen.list.viewmodel.BaseListViewModel

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