package com.materiiapps.gloom.ui.viewmodels.list

import com.materiiapps.gloom.gql.SponsoringQuery
import com.materiiapps.gloom.domain.models.ModelUser
import com.materiiapps.gloom.domain.repository.GraphQLRepository
import com.materiiapps.gloom.rest.utils.getOrNull
import com.materiiapps.gloom.ui.viewmodels.list.base.BaseListViewModel

class SponsoringViewModel(
    private val repo: GraphQLRepository,
    private val username: String
) : BaseListViewModel<ModelUser, SponsoringQuery.Data?>() {

    override suspend fun loadPage(cursor: String?) =
        repo.getSponsoring(username, cursor).getOrNull()

    override fun getCursor(data: SponsoringQuery.Data?) =
        data?.repositoryOwner?.onUser?.sponsoring?.pageInfo?.endCursor
            ?: data?.repositoryOwner?.onOrganization?.sponsoring?.pageInfo?.endCursor

    override fun createItems(data: SponsoringQuery.Data?): List<ModelUser> {
        val nodes = mutableListOf<ModelUser>()
        data?.repositoryOwner?.onUser?.sponsoring?.nodes?.forEach {
            if (it != null) nodes.add(ModelUser.fromSponsoringQuery(it))
        }
        data?.repositoryOwner?.onOrganization?.sponsoring?.nodes?.forEach {
            if (it != null) nodes.add(ModelUser.fromSponsoringQuery(it))
        }
        return nodes
    }

}