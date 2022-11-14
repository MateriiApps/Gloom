package com.materiapps.gloom.ui.viewmodels.list

import com.materiapps.gloom.StarredReposQuery
import com.materiapps.gloom.domain.models.ModelRepo
import com.materiapps.gloom.domain.repository.GraphQLRepository
import com.materiapps.gloom.rest.utils.getOrNull
import com.materiapps.gloom.ui.viewmodels.list.base.BaseListViewModel

class StarredReposListViewModel(
    private val repo: GraphQLRepository,
    private val username: String
) : BaseListViewModel<ModelRepo, StarredReposQuery.Data?>() {

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