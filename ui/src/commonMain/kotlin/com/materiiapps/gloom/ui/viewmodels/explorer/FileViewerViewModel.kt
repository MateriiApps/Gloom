package com.materiiapps.gloom.ui.viewmodels.explorer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.utils.fold
import com.materiiapps.gloom.gql.fragment.RepoFile
import kotlinx.coroutines.launch

class FileViewerViewModel(
    private val input: Input,
    private val gqlRepo: GraphQLRepository
): ScreenModel {

    data class Input(val owner: String, val name: String, val branch: String, val path: String)

    var file by mutableStateOf<RepoFile?>(null)

    var isLoading by mutableStateOf(false)
    var hasError by mutableStateOf(false)

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

}