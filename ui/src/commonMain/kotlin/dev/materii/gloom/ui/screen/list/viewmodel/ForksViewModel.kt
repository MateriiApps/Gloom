package dev.materii.gloom.ui.screen.list.viewmodel

import dev.materii.gloom.api.model.ModelRepo
import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.getOrNull
import dev.materii.gloom.gql.RepoForksQuery

class ForksViewModel(
    private val repo: GraphQLRepository,
    private val username: String,
    private val repoName: String
): BaseListViewModel<ModelRepo, RepoForksQuery.Data?>() {

    override suspend fun loadPage(cursor: String?): RepoForksQuery.Data? {
        return repo.getRepoForks(username, repoName, cursor).getOrNull()
    }

    override fun getCursor(data: RepoForksQuery.Data?): String? {
        return data?.repository?.forks?.pageInfo?.endCursor
    }

    override fun createItems(data: RepoForksQuery.Data?): List<ModelRepo> {
        return data?.repository?.forks?.nodes?.mapNotNull {
            it?.let { ModelRepo.fromRepoForksQuery(it) }
        }.orEmpty()
    }

}