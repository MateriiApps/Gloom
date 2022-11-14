package com.materiapps.gloom.ui.viewmodels.profile

import com.materiapps.gloom.FollowersQuery
import com.materiapps.gloom.domain.models.ModelUser
import com.materiapps.gloom.domain.repository.GraphQLRepository
import com.materiapps.gloom.rest.utils.getOrNull
import com.materiapps.gloom.ui.viewmodels.list.base.BaseListViewModel

class FollowersViewModel(
    private val repo: GraphQLRepository,
    private val username: String
) : BaseListViewModel<ModelUser, FollowersQuery.Data?>() {

    override suspend fun loadPage(cursor: String?) = repo.getFollowers(username, cursor).getOrNull()

    override fun getCursor(data: FollowersQuery.Data?) = data?.user?.followers?.pageInfo?.endCursor

    override fun createItems(data: FollowersQuery.Data?): List<ModelUser> {
        val nodes = mutableListOf<ModelUser>()
        data?.user?.followers?.nodes?.forEach {
            if (it != null) nodes.add(ModelUser.fromFollowersQuery(it))
        }
        return nodes
    }

}