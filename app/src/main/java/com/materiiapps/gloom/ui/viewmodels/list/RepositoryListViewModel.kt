package com.materiiapps.gloom.ui.viewmodels.list

import com.materiiapps.gloom.gql.RepoListQuery
import com.materiiapps.gloom.api.models.ModelRepo
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.utils.getOrNull
import com.materiiapps.gloom.ui.viewmodels.list.base.BaseListViewModel

class RepositoryListViewModel(
    private val repo: GraphQLRepository,
    private val username: String
) : BaseListViewModel<ModelRepo, RepoListQuery.Data?>() {

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