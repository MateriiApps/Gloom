package dev.materii.gloom.ui.screen.repo.viewmodel

import dev.materii.gloom.api.model.ModelUser
import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.getOrNull
import dev.materii.gloom.gql.RepoContributorsQuery
import dev.materii.gloom.ui.screen.list.viewmodel.BaseListViewModel

class RepoContributorsViewModel(
    private val repo: GraphQLRepository,
    private val owner: String,
    private val repository: String
): BaseListViewModel<ModelUser, RepoContributorsQuery.Data?>() {

    override suspend fun loadPage(cursor: String?): RepoContributorsQuery.Data? {
        return repo.getRepoContributors(owner, repository, cursor).getOrNull()
    }

    override fun getCursor(data: RepoContributorsQuery.Data?): String? {
        return data?.repository?.contributors?.pageInfo?.endCursor
    }

    override fun createItems(data: RepoContributorsQuery.Data?): List<ModelUser> {
        return data?.repository?.contributors?.nodes
            ?.filterNotNull()
            .orEmpty()
            .map {
                ModelUser.fromContributorsQuery(it)
            }
    }

}