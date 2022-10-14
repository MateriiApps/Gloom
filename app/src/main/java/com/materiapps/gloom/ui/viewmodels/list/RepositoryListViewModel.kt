package com.materiapps.gloom.ui.viewmodels.list

import androidx.paging.*
import cafe.adriel.voyager.core.model.ScreenModel
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.materiapps.gloom.RepoListQuery
import com.materiapps.gloom.utils.scope

class RepositoryListViewModel(
    client: ApolloClient,
    private val username: String
) : ScreenModel {

    val repos = Pager(PagingConfig(pageSize = 30)) {
        object : PagingSource<String, RepoListQuery.Node>() {
            override suspend fun load(params: LoadParams<String>): LoadResult<String, RepoListQuery.Node> {
                val page = params.key

                val response =
                    client.query(RepoListQuery(username, cursor = Optional.present(page))).execute()

                val nextKey = response.data?.user?.repositories?.pageInfo?.endCursor

                val nodes = mutableListOf<RepoListQuery.Node>()
                response.data?.user?.repositories?.nodes?.forEach {
                    if (it != null) nodes.add(it)
                }

                return LoadResult.Page(
                    data = nodes,
                    nextKey = nextKey,
                    prevKey = null
                )
            }

            override fun getRefreshKey(state: PagingState<String, RepoListQuery.Node>): String? =
                state.anchorPosition?.let {
                    state.closestPageToPosition(it)?.prevKey
                }
        }
    }.flow.cachedIn(scope)

}