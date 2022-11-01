package com.materiapps.gloom.ui.viewmodels.list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.materiapps.gloom.RepoListQuery
import com.materiapps.gloom.domain.manager.AuthManager
import com.materiapps.gloom.domain.models.ModelRepo
import com.materiapps.gloom.rest.utils.GraphQLUtils.response
import com.materiapps.gloom.rest.utils.getOrNull

class RepositoryListViewModel(
    client: ApolloClient,
    authManager: AuthManager,
    private val username: String
) : ScreenModel {

    val repos = Pager(PagingConfig(pageSize = 30)) {
        object : PagingSource<String, ModelRepo>() {
            override suspend fun load(params: LoadParams<String>): LoadResult<String, ModelRepo> {
                val page = params.key

                val response =
                    client.query(RepoListQuery(username, cursor = Optional.present(page)))
                        .response().getOrNull()

                val nextKey = response?.user?.repositories?.pageInfo?.endCursor

                val nodes = mutableListOf<ModelRepo>()
                response?.user?.repositories?.nodes?.forEach {
                    if (it != null) nodes.add(ModelRepo.fromRepoListQuery(it))
                }

                return LoadResult.Page(
                    data = nodes,
                    nextKey = nextKey,
                    prevKey = null
                )
            }

            override fun getRefreshKey(state: PagingState<String, ModelRepo>): String? =
                state.anchorPosition?.let {
                    state.closestPageToPosition(it)?.prevKey
                }
        }
    }.flow.cachedIn(coroutineScope)

}