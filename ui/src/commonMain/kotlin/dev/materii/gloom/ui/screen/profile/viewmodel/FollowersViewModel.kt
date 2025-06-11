package dev.materii.gloom.ui.screen.profile.viewmodel

import dev.materii.gloom.api.model.ModelUser
import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.getOrNull
import dev.materii.gloom.gql.FollowersQuery
import dev.materii.gloom.ui.screen.list.viewmodel.BaseListViewModel

class FollowersViewModel(
    private val repo: GraphQLRepository,
    private val username: String
): BaseListViewModel<ModelUser, FollowersQuery.Data?>() {

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