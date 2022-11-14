package com.materiapps.gloom.ui.viewmodels.list

import com.materiapps.gloom.RepoListQuery
import com.materiapps.gloom.domain.models.ModelRepo
import com.materiapps.gloom.domain.repository.GraphQLRepository
import com.materiapps.gloom.rest.utils.getOrNull
import com.materiapps.gloom.ui.viewmodels.list.base.BaseListViewModel

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