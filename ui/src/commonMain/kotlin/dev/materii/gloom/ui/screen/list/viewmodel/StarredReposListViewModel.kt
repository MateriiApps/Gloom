package dev.materii.gloom.ui.screen.list.viewmodel

import dev.materii.gloom.api.model.ModelRepo
import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.getOrNull
import dev.materii.gloom.gql.StarredReposQuery

class StarredReposListViewModel(
    private val repo: GraphQLRepository,
    private val username: String
): BaseListViewModel<ModelRepo, StarredReposQuery.Data?>() {

    override suspend fun loadPage(cursor: String?) =
        repo.getStarredRepositories(username, cursor).getOrNull()

    override fun getCursor(data: StarredReposQuery.Data?) =
        data?.user?.starredRepositories?.pageInfo?.endCursor

    override fun createItems(data: StarredReposQuery.Data?): List<ModelRepo> {
        val nodes = mutableListOf<ModelRepo>()
        data?.user?.starredRepositories?.nodes?.forEach {
            if (it != null) nodes.add(ModelRepo.fromStarredReposQuery(it))
        }
        return nodes
    }

}