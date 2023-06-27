package com.materiiapps.gloom.ui.viewmodels.release

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.materiiapps.gloom.domain.repository.GraphQLRepository
import com.materiiapps.gloom.gql.ReleaseDetailsQuery
import com.materiiapps.gloom.gql.fragment.ReleaseAssetFragment
import com.materiiapps.gloom.gql.fragment.ReleaseDetails
import com.materiiapps.gloom.rest.utils.getOrNull
import com.materiiapps.gloom.ui.viewmodels.list.base.BaseListViewModel

class ReleaseViewModel(
    private val repo: GraphQLRepository,
    nameAndTag: Triple<String, String, String>
) : BaseListViewModel<ReleaseAssetFragment, ReleaseDetailsQuery.Data?>() {

    val owner = nameAndTag.first
    val name = nameAndTag.second
    val tag = nameAndTag.third

    var details by mutableStateOf<ReleaseDetails?>(null)
        private set

    override suspend fun loadPage(cursor: String?): ReleaseDetailsQuery.Data? =
        repo.getReleaseDetails(owner, name, tag, cursor)
            .getOrNull()
            .also { details = it?.repository?.release?.releaseDetails }

    override fun getCursor(data: ReleaseDetailsQuery.Data?): String? =
        data?.repository?.release?.releaseDetails?.releaseAssets?.pageInfo?.endCursor

    override fun createItems(data: ReleaseDetailsQuery.Data?): List<ReleaseAssetFragment> =
        data?.repository?.release?.releaseDetails?.releaseAssets?.nodes?.mapNotNull { it?.releaseAssetFragment }
            ?: emptyList()


}