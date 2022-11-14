package com.materiapps.gloom.ui.viewmodels.list

import com.materiapps.gloom.JoinedOrgsQuery
import com.materiapps.gloom.domain.models.ModelUser
import com.materiapps.gloom.domain.repository.GraphQLRepository
import com.materiapps.gloom.rest.utils.getOrNull
import com.materiapps.gloom.ui.viewmodels.list.base.BaseListViewModel

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