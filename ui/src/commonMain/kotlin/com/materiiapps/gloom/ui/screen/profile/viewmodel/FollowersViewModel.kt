package com.materiiapps.gloom.ui.screen.profile.viewmodel

import com.materiiapps.gloom.api.model.ModelUser
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.util.getOrNull
import com.materiiapps.gloom.gql.FollowersQuery
import com.materiiapps.gloom.ui.screen.list.viewmodel.BaseListViewModel

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