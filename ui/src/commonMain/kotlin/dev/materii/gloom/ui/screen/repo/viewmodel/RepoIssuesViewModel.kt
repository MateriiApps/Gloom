package dev.materii.gloom.ui.screen.repo.viewmodel

import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.getOrNull
import dev.materii.gloom.gql.RepoIssuesQuery
import dev.materii.gloom.gql.fragment.IssueOverview
import dev.materii.gloom.ui.screen.list.viewmodel.BaseListViewModel

class RepoIssuesViewModel(
    private val gql: GraphQLRepository,
    nameWithOwner: Pair<String, String>
): BaseListViewModel<IssueOverview, RepoIssuesQuery.Data?>() {

    val owner = nameWithOwner.first
    val name = nameWithOwner.second

    override suspend fun loadPage(cursor: String?): RepoIssuesQuery.Data? =
        gql.getRepoIssues(owner, name, cursor).getOrNull()

    override fun getCursor(data: RepoIssuesQuery.Data?): String? =
        data?.repository?.issues?.pageInfo?.endCursor

    override fun createItems(data: RepoIssuesQuery.Data?): List<IssueOverview> =
        data?.repository?.issues?.nodes?.mapNotNull { it?.issueOverview } ?: emptyList()
}