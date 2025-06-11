package dev.materii.gloom.ui.screen.list.viewmodel

import dev.materii.gloom.api.model.ModelUser
import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.getOrNull
import dev.materii.gloom.gql.SponsoringQuery

class SponsoringViewModel(
    private val repo: GraphQLRepository,
    private val username: String
): BaseListViewModel<ModelUser, SponsoringQuery.Data?>() {

    override suspend fun loadPage(cursor: String?) =
        repo.getSponsoring(username, cursor).getOrNull()

    override fun getCursor(data: SponsoringQuery.Data?) =
        data?.repositoryOwner?.userSponsoringFragment?.sponsoring?.pageInfo?.endCursor
            ?: data?.repositoryOwner?.orgSponsoringFragment?.sponsoring?.pageInfo?.endCursor

    override fun createItems(data: SponsoringQuery.Data?): List<ModelUser> {
        val nodes = mutableListOf<ModelUser>()
        data?.repositoryOwner?.userSponsoringFragment?.sponsoring?.nodes?.forEach {
            if (it != null) nodes.add(ModelUser.fromSponsoringQuery(it))
        }
        data?.repositoryOwner?.orgSponsoringFragment?.sponsoring?.nodes?.forEach {
            if (it != null) nodes.add(ModelUser.fromSponsoringQuery(it))
        }
        return nodes
    }

}