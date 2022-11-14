package com.materiapps.gloom.ui.viewmodels.profile

import com.materiapps.gloom.FollowingQuery
import com.materiapps.gloom.domain.models.ModelUser
import com.materiapps.gloom.domain.repository.GraphQLRepository
import com.materiapps.gloom.rest.utils.getOrNull
import com.materiapps.gloom.ui.viewmodels.list.base.BaseListViewModel

class FollowingViewModel(
    private val repo: GraphQLRepository,
    private val username: String
) : BaseListViewModel<ModelUser, FollowingQuery.Data?>() {

    override suspend fun loadPage(cursor: String?) = repo.getFollowing(username, cursor).getOrNull()

    override fun getCursor(data: FollowingQuery.Data?) = data?.user?.following?.pageInfo?.endCursor

    override fun createItems(data: FollowingQuery.Data?): List<ModelUser> {
        val nodes = mutableListOf<ModelUser>()
        data?.user?.following?.nodes?.forEach {
            if (it != null) nodes.add(ModelUser.fromFollowingQuery(it))
        }
        return nodes
    }

}