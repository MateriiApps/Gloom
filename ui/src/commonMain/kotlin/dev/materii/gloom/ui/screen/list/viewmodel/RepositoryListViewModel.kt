package dev.materii.gloom.ui.screen.list.viewmodel

import dev.materii.gloom.api.model.ModelRepo
import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.getOrNull
import dev.materii.gloom.gql.RepoListQuery

class RepositoryListViewModel(
    private val repo: GraphQLRepository,
    private val username: String
): BaseListViewModel<ModelRepo, RepoListQuery.Data?>() {

    override suspend fun loadPage(cursor: String?) =
        repo.getRepositoriesForUser(username, cursor).getOrNull()

    override fun getCursor(data: RepoListQuery.Data?) =
        data?.repositoryOwner?.repositories?.pageInfo?.endCursor

    override fun createItems(data: RepoListQuery.Data?): List<ModelRepo> {
        val nodes = mutableListOf<ModelRepo>()
        data?.repositoryOwner?.repositories?.nodes?.forEach {
            if (it != null) nodes.add(ModelRepo.fromRepoListQuery(it))
        }
        return nodes
    }

}