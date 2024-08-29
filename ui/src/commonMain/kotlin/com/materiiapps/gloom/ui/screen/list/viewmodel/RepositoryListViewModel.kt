package com.materiiapps.gloom.ui.screen.list.viewmodel

import com.materiiapps.gloom.api.model.ModelRepo
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.util.getOrNull
import com.materiiapps.gloom.gql.RepoListQuery

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