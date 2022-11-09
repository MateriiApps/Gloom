package com.materiapps.gloom.ui.viewmodels.list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.materiapps.gloom.domain.models.ModelUser
import com.materiapps.gloom.domain.repository.GraphQLRepository
import com.materiapps.gloom.rest.utils.getOrNull

class OrgListViewModel(
    repo: GraphQLRepository,
    private val username: String
) : ScreenModel {

    val orgs = Pager(PagingConfig(pageSize = 30)) {
        object : PagingSource<String, ModelUser>() {
            override suspend fun load(params: LoadParams<String>): LoadResult<String, ModelUser> {
                val page = params.key

                val response = repo.getJoinedOrgs(username, page).getOrNull()

                val nextKey = response?.user?.organizations?.pageInfo?.endCursor

                val nodes = mutableListOf<ModelUser>()
                response?.user?.organizations?.nodes?.forEach {
                    if (it != null) nodes.add(ModelUser.fromJoinedOrgsQuery(it))
                }

                return LoadResult.Page(
                    data = nodes,
                    nextKey = nextKey,
                    prevKey = null
                )
            }

            override fun getRefreshKey(state: PagingState<String, ModelUser>): String? =
                state.anchorPosition?.let {
                    state.closestPageToPosition(it)?.prevKey
                }
        }
    }.flow.cachedIn(coroutineScope)

}