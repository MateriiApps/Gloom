package dev.materii.gloom.ui.screen.repo.viewmodel

import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.getOrNull
import dev.materii.gloom.gql.RepoCommitsQuery
import dev.materii.gloom.gql.fragment.CommitDetails
import dev.materii.gloom.ui.screen.list.viewmodel.BaseListViewModel

class RepoCommitsViewModel(
    private val gql: GraphQLRepository,
    private val id: String,
    private val branch: String
) : BaseListViewModel<CommitDetails, RepoCommitsQuery.Data?>() {

    override suspend fun loadPage(cursor: String?): RepoCommitsQuery.Data? {
        return gql.getRepoCommits(id, branch, cursor).getOrNull()
    }

    override fun getCursor(data: RepoCommitsQuery.Data?): String? {
        return data?.node?.onRepository?.gitObject?.onCommit?.history?.pageInfo?.endCursor
    }

    override fun createItems(data: RepoCommitsQuery.Data?): List<CommitDetails> {
        return data?.node?.onRepository?.gitObject?.onCommit?.history?.nodes?.mapNotNull { it?.commitDetails }.orEmpty()
    }

}