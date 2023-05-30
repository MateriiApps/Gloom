package com.materiiapps.gloom.ui.viewmodels.list

import com.materiiapps.gloom.gql.StarredReposQuery
import com.materiiapps.gloom.domain.models.ModelRepo
import com.materiiapps.gloom.domain.repository.GraphQLRepository
import com.materiiapps.gloom.rest.utils.getOrNull
import com.materiiapps.gloom.ui.viewmodels.list.base.BaseListViewModel

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