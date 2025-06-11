package dev.materii.gloom.ui.screen.release.viewmodel

import dev.materii.gloom.gql.ReleaseDetailsQuery
import dev.materii.gloom.gql.fragment.ReleaseAssetFragment
import dev.materii.gloom.gql.fragment.ReleaseDetails
import dev.materii.gloom.gql.type.ReactionContent
import dev.materii.gloom.ui.screen.list.viewmodel.BaseListViewModel
import java.io.File

expect class ReleaseViewModel: BaseListViewModel<ReleaseAssetFragment, ReleaseDetailsQuery.Data?> {

    val owner: String
    val name: String
    val tag: String

    var details: ReleaseDetails?
        private set

    var apkFile: File?
        private set

    override suspend fun loadPage(cursor: String?): ReleaseDetailsQuery.Data?

    override fun getCursor(data: ReleaseDetailsQuery.Data?): String?

    override fun createItems(data: ReleaseDetailsQuery.Data?): List<ReleaseAssetFragment>


    fun react(reaction: ReactionContent, unreact: Boolean)

    fun downloadAsset(url: String, mimeType: String, onFinish: () -> Unit)

    fun clearApk()

    fun installApk()

}