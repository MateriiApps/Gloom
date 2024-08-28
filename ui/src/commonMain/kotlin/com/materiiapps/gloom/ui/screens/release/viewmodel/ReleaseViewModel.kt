package com.materiiapps.gloom.ui.screens.release.viewmodel

import com.materiiapps.gloom.gql.ReleaseDetailsQuery
import com.materiiapps.gloom.gql.fragment.ReleaseAssetFragment
import com.materiiapps.gloom.gql.fragment.ReleaseDetails
import com.materiiapps.gloom.gql.type.ReactionContent
import com.materiiapps.gloom.ui.screens.list.viewmodel.BaseListViewModel
import java.io.File

expect class ReleaseViewModel : BaseListViewModel<ReleaseAssetFragment, ReleaseDetailsQuery.Data?> {

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