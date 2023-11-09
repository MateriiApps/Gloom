package com.materiiapps.gloom.ui.viewmodels.explorer

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.utils.fold
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.gql.fragment.RepoFile
import kotlinx.coroutines.launch

@Stable
class FileViewerViewModel(
    private val input: Input,
    private val gqlRepo: GraphQLRepository,
    private val authManager: AuthManager
): ScreenModel {

    @Stable
    data class Input(val owner: String, val name: String, val branch: String, val path: String)

    var file by mutableStateOf<RepoFile?>(null)

    var isLoading by mutableStateOf(false)
    var hasError by mutableStateOf(false)

    var selectedLines: IntRange? by mutableStateOf(null)
    var selectedSnippet: String = ""

    init {
        getRepoFile()
    }

    fun getRepoFile() {
        with(input) {
            isLoading = true
            coroutineScope.launch {
                gqlRepo.getRepoFile(owner, name, branch, path).fold(
                    onSuccess = {
                        file = it
                    },
                    onError = { hasError = true }
                )
                isLoading = false
            }
        }
    }

    private fun getLinesPart(): String {
        if (selectedLines == null) return ""
        return buildString {
            append("#L${selectedLines!!.first}")
            if (selectedLines!!.count() > 1) append("-L${selectedLines!!.last}")
        } // Should be something like #L1-L10
    }

    fun getFileUrl(): String {
        return with(input) {
            "${authManager.currentAccount?.baseUrl ?: "https://github.com"}/$owner/$name/blob/$branch/$path${getLinesPart()}"
        }
    }

}