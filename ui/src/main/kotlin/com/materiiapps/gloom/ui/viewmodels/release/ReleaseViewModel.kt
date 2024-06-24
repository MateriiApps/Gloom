package com.materiiapps.gloom.ui.viewmodels.release

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.screenModelScope
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.utils.getOrNull
import com.materiiapps.gloom.domain.manager.DownloadManager
import com.materiiapps.gloom.gql.ReleaseDetailsQuery
import com.materiiapps.gloom.gql.fragment.ReleaseAssetFragment
import com.materiiapps.gloom.gql.fragment.ReleaseDetails
import com.materiiapps.gloom.gql.type.ReactionContent
import com.materiiapps.gloom.ui.utils.installApks
import com.materiiapps.gloom.ui.viewmodels.list.base.BaseListViewModel
import kotlinx.coroutines.launch
import java.io.File

class ReleaseViewModel(
    private val repo: GraphQLRepository,
    private val downloadManager: DownloadManager,
    private val context: Context,
    nameAndTag: Triple<String, String, String>
) : BaseListViewModel<ReleaseAssetFragment, ReleaseDetailsQuery.Data?>() {

    val owner = nameAndTag.first
    val name = nameAndTag.second
    val tag = nameAndTag.third

    var details by mutableStateOf<ReleaseDetails?>(null)
        private set

    var apkFile by mutableStateOf<File?>(null)
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


    fun react(reaction: ReactionContent, unreact: Boolean) {
        details?.let {
            screenModelScope.launch {
                if (unreact) {
                    repo.unreact(it.id, reaction)
                } else {
                    repo.react(it.id, reaction)
                }
            }
        }
    }

    fun downloadAsset(url: String, mimeType: String, onFinish: () -> Unit) {
        downloadManager.download(url) {
            onFinish()
            if (mimeType == "application/vnd.android.package-archive") apkFile = File(it)
        }
    }

    fun clearApk() {
        apkFile = null
    }

    fun installApk() {
        apkFile?.let { context.installApks(it) }
    }

}