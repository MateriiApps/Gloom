package com.materiiapps.gloom.ui.screen.list.viewmodel

import com.materiiapps.gloom.api.model.ModelUser
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.util.getOrNull
import com.materiiapps.gloom.gql.JoinedOrgsQuery

class OrgListViewModel(
    private val repo: GraphQLRepository,
    private val username: String
) : BaseListViewModel<ModelUser, JoinedOrgsQuery.Data?>() {

    override suspend fun loadPage(cursor: String?) =
        repo.getJoinedOrgs(username, cursor).getOrNull()

    override fun getCursor(data: JoinedOrgsQuery.Data?) =
        data?.user?.organizations?.pageInfo?.endCursor

    override fun createItems(data: JoinedOrgsQuery.Data?): List<ModelUser> {
        val nodes = mutableListOf<ModelUser>()
        data?.user?.organizations?.nodes?.forEach {
            if (it != null) nodes.add(ModelUser.fromJoinedOrgsQuery(it))
        }
        return nodes
    }

}