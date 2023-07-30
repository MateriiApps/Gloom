package com.materiiapps.gloom.ui.viewmodels.list

import com.materiiapps.gloom.gql.JoinedOrgsQuery
import com.materiiapps.gloom.api.models.ModelUser
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.utils.getOrNull
import com.materiiapps.gloom.ui.viewmodels.list.base.BaseListViewModel

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