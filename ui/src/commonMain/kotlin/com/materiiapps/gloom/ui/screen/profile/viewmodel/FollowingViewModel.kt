package com.materiiapps.gloom.ui.screen.profile.viewmodel

import com.materiiapps.gloom.api.model.ModelUser
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.util.getOrNull
import com.materiiapps.gloom.gql.FollowingQuery
import com.materiiapps.gloom.ui.screen.list.viewmodel.BaseListViewModel

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